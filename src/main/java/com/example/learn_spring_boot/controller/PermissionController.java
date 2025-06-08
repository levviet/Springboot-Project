package com.example.learn_spring_boot.controller;

import com.example.learn_spring_boot.dto.ApiResponse;
import com.example.learn_spring_boot.dto.request.PermissionRequest;
import com.example.learn_spring_boot.dto.response.PermissionResponse;
import com.example.learn_spring_boot.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
	private final PermissionService permissionService;

	@PostMapping
	public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
		ApiResponse<PermissionResponse> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(permissionService.create(request));
		return response;
	}

	@GetMapping
	public ApiResponse<List<PermissionResponse>> getAll() {
		ApiResponse<List<PermissionResponse>> response = new ApiResponse<>();

		response.setMessage("Success");
		response.setResult(permissionService.getAll());
		return response;
	}

	@DeleteMapping("/{permission}")
	ApiResponse<String> deleteUser(@PathVariable("permission") String permission) {
		ApiResponse<String> response = new ApiResponse<>();

		response.setMessage("Permission deleted successfully");
		permissionService.delete(permission);
		return response;
	}
}
