package com.example.learn_spring_boot.service;

import com.example.learn_spring_boot.dto.request.RoleRequest;
import com.example.learn_spring_boot.dto.response.RoleResponse;
import com.example.learn_spring_boot.entity.Permission;
import com.example.learn_spring_boot.entity.Role;
import com.example.learn_spring_boot.mapper.RoleMapper;
import com.example.learn_spring_boot.repository.PermissionRepository;
import com.example.learn_spring_boot.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final RoleMapper roleMapper;

	public RoleResponse create(RoleRequest request) {
		Role role = roleMapper.toRole(request);

		List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
		role.setPermissions(new HashSet<>(permissions));

		return roleMapper.toRoleResponse(roleRepository.save(role));
	}

	public List<RoleResponse> getAll() {
		return roleRepository.findAll()
				.stream()
				.map(roleMapper::toRoleResponse)
				.collect(Collectors.toList());
	}

	public void delete(String id) {
		roleRepository.deleteById(id);
	}
}
