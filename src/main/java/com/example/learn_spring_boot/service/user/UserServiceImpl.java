package com.example.learn_spring_boot.service.user;

import com.example.learn_spring_boot.dto.request.CreateUserRequest;
import com.example.learn_spring_boot.dto.request.UpdateUserRequest;
import com.example.learn_spring_boot.dto.response.UserResponse;
import com.example.learn_spring_boot.entity.User;
import com.example.learn_spring_boot.enums.Role;
import com.example.learn_spring_boot.exception.AppException;
import com.example.learn_spring_boot.exception.ErrorCode;
import com.example.learn_spring_boot.mapper.UserMapper;
import com.example.learn_spring_boot.repository.RoleRepository;
import com.example.learn_spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {
	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final RoleRepository roleRepository;

	public UserResponse create(CreateUserRequest request) {
		User user = userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		HashSet<String> roles = new HashSet<>();
		roles.add(Role.USER.name());

		// user.setRoles(roles);
		try {
			user = userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}

		return userMapper.toUserResponse(user);
	}

	public UserResponse update(String userId, UpdateUserRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		userMapper.updateUser(user, request);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		var role = roleRepository.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(role));

		return userMapper.toUserResponse(userRepository.save(user));
	}

	public void delete(String userId) {
		userRepository.deleteById(userId);
	}

	//	@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAuthority('CREATE_POST')")
	public List<UserResponse> findAll() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();

		log.info("Username: {}", authentication.getName());
		authentication.getAuthorities().forEach(role -> log.info(role.getAuthority()));

		return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
	}

	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse findById(String id) {
		return userMapper.toUserResponse(
				userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
	}

	public UserResponse getUserInfo() {
		var context = SecurityContextHolder.getContext();

		String username = context.getAuthentication().getName();

		User user = userRepository
				.findUserByUsername(username)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		return userMapper.toUserResponse(user);
	}
}
