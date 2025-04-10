package com.example.learn_spring_boot.service;

import com.example.learn_spring_boot.dto.request.CreateUserRequest;
import com.example.learn_spring_boot.dto.request.UpdateUserRequest;
import com.example.learn_spring_boot.dto.response.UserResponse;
import com.example.learn_spring_boot.entity.User;
import com.example.learn_spring_boot.exception.AppException;
import com.example.learn_spring_boot.exception.ErrorCode;
import com.example.learn_spring_boot.mapper.UserMapper;
import com.example.learn_spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	private final UserMapper userMapper;

	public User create(CreateUserRequest request) {

		if (userRepository.existsUserByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}

		User user = userMapper.toUser(request);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		return userRepository.save(user);
	}

	public UserResponse update(String userId, UpdateUserRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		userMapper.updateUser(user, request);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		return userMapper.toUserResponse(userRepository.save(user));
	}

	public void delete(String userId) {
		userRepository.deleteById(userId);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public UserResponse findById(String id) {
		return userMapper.toUserResponse(userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found")));
	}
}