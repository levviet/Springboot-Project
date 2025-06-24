package com.example.learn_spring_boot.dto.request;

import com.example.learn_spring_boot.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
	@Size(min = 3, message = "USERNAME_INVALID")
	private String username;

	@Size(min = 8, message = "PASSWORD_INVALID")
	private String password;
	private String firstName;
	private String lastName;

	@DobConstraint(min = 18, message = "INVALID_DOB")
	private LocalDate dob;
}
