package com.example.learn_spring_boot.controller;

import com.example.learn_spring_boot.dto.ApiResponse;
import com.example.learn_spring_boot.dto.request.AuthenticationRequest;
import com.example.learn_spring_boot.dto.request.IntrospectRequest;
import com.example.learn_spring_boot.dto.request.LogoutRequest;
import com.example.learn_spring_boot.dto.request.RefreshRequest;
import com.example.learn_spring_boot.dto.response.AuthenticationResponse;
import com.example.learn_spring_boot.dto.response.IntrospectResponse;
import com.example.learn_spring_boot.service.authen.AuthenticationServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationServiceImpl authenticationService;

	@PostMapping("/login")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		ApiResponse<AuthenticationResponse> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(authenticationService.authenticate(request));

		return response;
	}

	@PostMapping("/introspect")
	ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
			throws ParseException, JOSEException {
		ApiResponse<IntrospectResponse> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(authenticationService.introspect(request));

		return response;
	}

	@PostMapping("/logout")
	ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
		ApiResponse<Void> response = new ApiResponse<>();
		authenticationService.logout(request);

		response.setMessage("Success");
		return response;
	}

	@PostMapping("/refresh")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
			throws ParseException, JOSEException {
		ApiResponse<AuthenticationResponse> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(authenticationService.refreshToken(request));

		return response;
	}
}
