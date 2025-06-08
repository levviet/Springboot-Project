package com.example.learn_spring_boot.service;

import com.example.learn_spring_boot.dto.request.PermissionRequest;
import com.example.learn_spring_boot.dto.response.PermissionResponse;
import com.example.learn_spring_boot.entity.Permission;
import com.example.learn_spring_boot.mapper.PermissionMapper;
import com.example.learn_spring_boot.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	public PermissionResponse create(PermissionRequest request) {
		Permission permission = permissionMapper.toPermission(request);

		return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
	}

	public List<PermissionResponse> getAll() {
		var permissions = permissionRepository.findAll();

		return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
	}

	public void delete(String permission) {
		permissionRepository.deleteById(permission);
	}
}
