package com.example.learn_spring_boot.configuration;

import com.example.learn_spring_boot.entity.User;
import com.example.learn_spring_boot.enums.Role;
import com.example.learn_spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

	private final PasswordEncoder passwordEncoder;

	@Bean
	ApplicationRunner applicationRunner(UserRepository userRepository) {
		return args -> {
			if (userRepository.findUserByUsername("admin").isEmpty()) {
				HashSet<String> roles = new HashSet<>();
				roles.add(Role.ADMIN.name());

				User user = User.builder()
						.username("admin")
						.password(passwordEncoder.encode("admin"))
						.roles(roles)
						.build();

				userRepository.save(user);
			}
		};
	}
}
