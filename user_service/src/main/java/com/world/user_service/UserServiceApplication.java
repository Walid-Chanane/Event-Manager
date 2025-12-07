package com.world.user_service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.world.user_service.role.Role;
import com.world.user_service.role.RoleRepository;
import com.world.user_service.user.User;
import com.world.user_service.user.UserRepository;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository,RoleRepository roleRepository, PasswordEncoder passwordEncoder){
		return runner -> {
			Role role = Role.builder().name("ROLE_PARTICIPANT").build();
			roleRepository.save(role);
			Role anotherRole = Role.builder().name("ROLE_EMPLOYEE").build();
			roleRepository.save(anotherRole);
			Role adminRole = Role.builder().name("ROLE_ADMIN").build();
			roleRepository.save(adminRole);

			User admin = User.builder().firstName("admin").lastName("admin").roles(List.of(adminRole))
				.email("admin@gmail.com").password(passwordEncoder.encode("password")).enabled(true)
				.dateOfBirth(LocalDate.now()).build();
			userRepository.save(admin);
		};
	}

}
