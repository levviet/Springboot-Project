package com.example.learn_spring_boot.service;

import com.example.learn_spring_boot.dto.request.AuthenticationRequest;
import com.example.learn_spring_boot.entity.User;
import com.example.learn_spring_boot.exception.AppException;
import com.example.learn_spring_boot.exception.ErrorCode;
import com.example.learn_spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;

	public boolean authenticate(AuthenticationRequest request) {
		User user = userRepository.findUserByUsername(request.getUsername())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		return passwordEncoder.matches(request.getPassword(), user.getPassword());
	}
}