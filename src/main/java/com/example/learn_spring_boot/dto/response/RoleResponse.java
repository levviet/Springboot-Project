package com.example.learn_spring_boot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
	private String name;
	private String description;
	private Set<PermissionResponse> permissions;
}
