package com.example.learn_spring_boot.service;

import com.example.learn_spring_boot.dto.request.AuthenticationRequest;
import com.example.learn_spring_boot.dto.request.IntrospectRequest;
import com.example.learn_spring_boot.dto.request.LogoutRequest;
import com.example.learn_spring_boot.dto.request.RefreshRequest;
import com.example.learn_spring_boot.dto.response.AuthenticationResponse;
import com.example.learn_spring_boot.dto.response.IntrospectResponse;
import com.example.learn_spring_boot.entity.InvalidatedToken;
import com.example.learn_spring_boot.entity.User;
import com.example.learn_spring_boot.exception.AppException;
import com.example.learn_spring_boot.exception.ErrorCode;
import com.example.learn_spring_boot.repository.InvalidatedTokenRepository;
import com.example.learn_spring_boot.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
	private final UserRepository userRepository;

	private final InvalidatedTokenRepository invalidatedTokenRepository;

	@NonFinal
	@Value("${jwt.signerKey}")
	protected String SIGNER_KEY;

	public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
		var token = request.getToken();
		boolean isValid = true;

		try {
			verifyToken(token);

		} catch (AppException e) {
			isValid = false;
		}

		return IntrospectResponse.builder()
				.valid(isValid)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		User user = userRepository.findUserByUsername(request.getUsername())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (!authenticated) {
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		}

		var token = generateToken(user);

		return AuthenticationResponse.builder()
				.token(token)
				.authenticated(true)
				.build();
	}

	public void logout(LogoutRequest request) throws ParseException, JOSEException {
		var signedToken = verifyToken(request.getToken());

		String jit = signedToken.getJWTClaimsSet().getJWTID();
		Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();

		InvalidatedToken invalidatedToken = InvalidatedToken.builder()
				.id(jit)
				.expiryTime(expiryTime)
				.build();

		invalidatedTokenRepository.save(invalidatedToken);
	}

	public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
		var signedJWT = verifyToken(request.getToken());

		var jit = signedJWT.getJWTClaimsSet().getJWTID();
		var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

		InvalidatedToken invalidatedToken = InvalidatedToken.builder()
				.id(jit)
				.expiryTime(expiryTime)
				.build();

		invalidatedTokenRepository.save(invalidatedToken);

		var username = signedJWT.getJWTClaimsSet().getSubject();
		User user = userRepository.findUserByUsername(username).orElseThrow(
				() -> new AppException(ErrorCode.UNAUTHENTICATED)
		);

		var token = generateToken(user);

		return AuthenticationResponse.builder()
				.token(token)
				.authenticated(true)
				.build();
	}

	private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
		JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

		SignedJWT signedJWT = SignedJWT.parse(token);

		Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

		var verified = signedJWT.verify(verifier);

		if (!verified && expiration.after(new Date())) {
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		}

		if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		}

		return signedJWT;
	}

	private String generateToken(User user) {
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject(user.getUsername())
				.issuer("levviet")
				.issueTime(new Date())
				.expirationTime(new Date(
						Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
				))
				.jwtID(UUID.randomUUID().toString())
				.claim("scope", buildScope(user))
				.build();

		Payload payload = new Payload(jwtClaimsSet.toJSONObject());

		JWSObject jwsObject = new JWSObject(header, payload);

		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			log.error("Can not generate token", e);
			throw new RuntimeException(e);
		}
	}

	private String buildScope(User user) {
		StringJoiner stringJoiner = new StringJoiner(" ");

		if (!CollectionUtils.isEmpty(user.getRoles())) {
			user.getRoles().forEach(role -> {
				stringJoiner.add("ROLE_" + role.getName());
				if (!CollectionUtils.isEmpty(role.getPermissions())) {
					role.getPermissions()
							.forEach(permission -> stringJoiner.add(permission.getName()));
				}
			});
		}

		return stringJoiner.toString();
	}
}