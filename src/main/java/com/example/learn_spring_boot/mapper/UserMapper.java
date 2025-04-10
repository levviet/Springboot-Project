package com.example.learn_spring_boot.mapper;

import com.example.learn_spring_boot.dto.request.CreateUserRequest;
import com.example.learn_spring_boot.dto.request.UpdateUserRequest;
import com.example.learn_spring_boot.dto.response.UserResponse;
import com.example.learn_spring_boot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toUser(CreateUserRequest request);

	void updateUser(@MappingTarget User user, UpdateUserRequest request);

	UserResponse toUserResponse(User user);
}