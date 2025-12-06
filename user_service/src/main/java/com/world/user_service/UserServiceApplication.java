package com.world.user_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.world.user_service.role.Role;
import com.world.user_service.role.RoleRepository;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository){
		return runner -> {
			Role role = Role.builder().name("ROLE_PARTICIPANT").build();
			roleRepository.save(role);
		};
	}

}
