package com.example.learn_spring_boot.controller;

import com.example.learn_spring_boot.dto.ApiResponse;
import com.example.learn_spring_boot.dto.request.AuthenticationRequest;
import com.example.learn_spring_boot.dto.request.IntrospectRequest;
import com.example.learn_spring_boot.dto.response.AuthenticationResponse;
import com.example.learn_spring_boot.dto.response.IntrospectResponse;
import com.example.learn_spring_boot.service.AuthenticationService;
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
	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		ApiResponse<AuthenticationResponse> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(authenticationService.authenticate(request));

		return response;
	}

	@PostMapping("/introspect")
	ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
		ApiResponse<IntrospectResponse> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(authenticationService.introspect(request));

		return response;
	}
}