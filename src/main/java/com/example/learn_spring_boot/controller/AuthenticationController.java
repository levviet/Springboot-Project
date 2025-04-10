package com.example.learn_spring_boot.controller;

import com.example.learn_spring_boot.dto.ApiResponse;
import com.example.learn_spring_boot.dto.request.AuthenticationRequest;
import com.example.learn_spring_boot.dto.response.AuthenticationResponse;
import com.example.learn_spring_boot.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		AuthenticationResponse authResponse = AuthenticationResponse.builder()
				.authenticated(authenticationService.authenticate(request))
				.build();

		return ApiResponse.<AuthenticationResponse>builder()
				.result(authResponse)
				.build();
	}
}