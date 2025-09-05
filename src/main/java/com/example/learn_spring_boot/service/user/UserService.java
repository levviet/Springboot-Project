package com.example.learn_spring_boot.service.user;

import java.util.List;

import com.example.learn_spring_boot.dto.request.CreateUserRequest;
import com.example.learn_spring_boot.dto.request.UpdateUserRequest;
import com.example.learn_spring_boot.dto.response.UserResponse;

public interface UserService {
    UserResponse create(CreateUserRequest request);

    UserResponse update(String userId, UpdateUserRequest request);

    void delete(String userId);

    List<UserResponse> findAll();

    UserResponse findById(String id);

    UserResponse getUserInfo();
}
