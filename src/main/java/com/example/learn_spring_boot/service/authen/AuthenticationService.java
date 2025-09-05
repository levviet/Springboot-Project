package com.example.learn_spring_boot.service.authen;

import com.example.learn_spring_boot.dto.request.AuthenticationRequest;
import com.example.learn_spring_boot.dto.request.IntrospectRequest;
import com.example.learn_spring_boot.dto.request.LogoutRequest;
import com.example.learn_spring_boot.dto.request.RefreshRequest;
import com.example.learn_spring_boot.dto.response.AuthenticationResponse;
import com.example.learn_spring_boot.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
	IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

	AuthenticationResponse authenticate(AuthenticationRequest request);

	void logout(LogoutRequest request) throws ParseException, JOSEException;

	AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
