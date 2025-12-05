package com.world.user_service.auth;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.world.user_service.role.Role;
import com.world.user_service.role.RoleRepository;
import com.world.user_service.user.User;
import com.world.user_service.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegistrationRequest request){
        
        Role role = roleRepository.findByName("ROLE_PARTICIPANT")
                    .orElseThrow(() -> new IllegalStateException("Role 'PARTICIPANT' has not been initialized."));
        
        User user = User.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .dateOfBirth(request.dateOfBirth())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .roles(List.of(role))
            .build();
        userRepository.save(user);
    }

}
