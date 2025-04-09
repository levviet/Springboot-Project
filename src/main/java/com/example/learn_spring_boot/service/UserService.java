package com.example.learn_spring_boot.service;

import com.example.learn_spring_boot.dto.request.CreateUserRequest;
import com.example.learn_spring_boot.dto.request.UpdateUserRequest;
import com.example.learn_spring_boot.entity.User;
import com.example.learn_spring_boot.exception.AppException;
import com.example.learn_spring_boot.exception.ErrorCode;
import com.example.learn_spring_boot.mapper.UserMapper;
import com.example.learn_spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public User create(CreateUserRequest request) {

		if (userRepository.existsUserByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}

		User user = userMapper.toUser(request);

		return userRepository.save(user);
	}

	public User update(String userId, UpdateUserRequest request) {
		User user = findById(userId);

		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());
		return userRepository.save(user);
	}

	public void delete(String userId) {
		userRepository.deleteById(userId);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
}
