package com.world.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import lombok.NoArgsConstructor;

@Configuration
@NoArgsConstructor
public class BeansConfig {
    
    @Bean
    public AuditorAware<Integer> auditAware(){
        return new UserServiceAuditAware();
    }
}
