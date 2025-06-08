package com.example.learn_spring_boot.dto.response;

import com.example.learn_spring_boot.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	private String id;
	private String username;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private Set<Role> roles;
}