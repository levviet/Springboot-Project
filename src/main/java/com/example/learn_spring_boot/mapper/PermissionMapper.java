package com.example.learn_spring_boot.mapper;

import org.mapstruct.Mapper;

import com.example.learn_spring_boot.dto.request.PermissionRequest;
import com.example.learn_spring_boot.dto.response.PermissionResponse;
import com.example.learn_spring_boot.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
