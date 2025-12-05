package com.world.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.NoArgsConstructor;

@Configuration
@NoArgsConstructor
public class BeansConfig {
    
    @Bean
    public AuditorAware<Integer> auditorAware(){
        return new UserServiceAuditAware();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
