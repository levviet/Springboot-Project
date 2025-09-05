package com.example.learn_spring_boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learn_spring_boot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsUserByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
