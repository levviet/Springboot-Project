package com.example.learn_spring_boot.controller;

import com.example.learn_spring_boot.dto.ApiResponse;
import com.example.learn_spring_boot.dto.request.CreateUserRequest;
import com.example.learn_spring_boot.dto.request.UpdateUserRequest;
import com.example.learn_spring_boot.dto.response.UserResponse;
import com.example.learn_spring_boot.entity.User;
import com.example.learn_spring_boot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/users")
	public ApiResponse<User> createUser(@RequestBody @Valid CreateUserRequest request) {
		ApiResponse<User> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(userService.create(request));
		return response;
	}

	@GetMapping("/users")
	List<User> getUsers() {
		return userService.findAll();
	}

	@GetMapping("/{userId}")
	UserResponse getUserById(@PathVariable("userId") String id) {
		return userService.findById(id);
	}

	@PutMapping("/{userId}")
	UserResponse updateUser(@PathVariable("userId") String id, @RequestBody UpdateUserRequest request) {
		return userService.update(id, request);
	}

	@DeleteMapping("/{userId}")
	String deleteUser(@PathVariable("userId") String id) {
		userService.delete(id);
		return "User deleted successfully";
	}
}