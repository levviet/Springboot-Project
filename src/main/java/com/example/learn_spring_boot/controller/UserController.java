package com.example.learn_spring_boot.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.learn_spring_boot.dto.ApiResponse;
import com.example.learn_spring_boot.dto.request.CreateUserRequest;
import com.example.learn_spring_boot.dto.request.UpdateUserRequest;
import com.example.learn_spring_boot.dto.response.UserResponse;
import com.example.learn_spring_boot.service.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/users")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();

        response.setMessage("Success");
        response.setResult(userService.create(request));
        return response;
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getUsers() {
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();

        response.setMessage("Success");
        response.setResult(userService.findAll());
        return response;
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("userId") String id) {
        ApiResponse<UserResponse> response = new ApiResponse<>();

        response.setMessage("Success");
        response.setResult(userService.findById(id));
        return response;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String id, @RequestBody UpdateUserRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();

        response.setMessage("Success");
        response.setResult(userService.update(id, request));
        return response;
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") String id) {
        ApiResponse<String> response = new ApiResponse<>();

        response.setMessage("User deleted successfully");
        userService.delete(id);
        return response;
    }

    @GetMapping("/userInfo")
    ApiResponse<UserResponse> getUserInfo() {
        ApiResponse<UserResponse> response = new ApiResponse<>();

        response.setMessage("Success");
        response.setResult(userService.getUserInfo());
        return response;
    }
}
