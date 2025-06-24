package com.example.learn_spring_boot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
	private String password;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	List<String> roles;
}
