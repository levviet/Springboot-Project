package com.example.learn_spring_boot.service.role;

import java.util.List;

import com.example.learn_spring_boot.dto.request.RoleRequest;
import com.example.learn_spring_boot.dto.response.RoleResponse;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();
}
