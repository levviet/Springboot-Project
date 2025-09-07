package com.example.learn_spring_boot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private LocalDate dob;

	@ManyToMany
	private Set<Role> roles;
}
