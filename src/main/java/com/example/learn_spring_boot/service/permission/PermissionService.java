package com.example.learn_spring_boot.service.permission;

import java.util.List;

import com.example.learn_spring_boot.dto.request.PermissionRequest;
import com.example.learn_spring_boot.dto.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();
}
