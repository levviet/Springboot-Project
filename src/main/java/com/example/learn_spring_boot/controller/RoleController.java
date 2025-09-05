package com.example.learn_spring_boot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.learn_spring_boot.dto.ApiResponse;
import com.example.learn_spring_boot.dto.request.RoleRequest;
import com.example.learn_spring_boot.dto.response.RoleResponse;
import com.example.learn_spring_boot.service.role.RoleServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();

        response.setMessage("Success");
        response.setResult(roleService.create(request));
        return response;
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        ApiResponse<List<RoleResponse>> response = new ApiResponse<>();

        response.setMessage("Success");
        response.setResult(roleService.getAll());
        return response;
    }

    @DeleteMapping("/{role}")
    ApiResponse<String> deleteUser(@PathVariable("role") String role) {
        ApiResponse<String> response = new ApiResponse<>();

        response.setMessage("Role deleted successfully");
        roleService.delete(role);
        return response;
    }
}
