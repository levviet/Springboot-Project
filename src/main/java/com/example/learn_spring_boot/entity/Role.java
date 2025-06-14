package com.example.learn_spring_boot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
	private String name;
	private String description;

	@ManyToMany
	Set<Permission> permissions;
}
