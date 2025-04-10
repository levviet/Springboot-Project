package com.example.learn_spring_boot.repository;

import com.example.learn_spring_boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Boolean existsUserByUsername(String username);

	Optional<User> findUserByUsername(String username);
}