package com.world.user_service.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.world.user_service.auth.RegistrationRequest;
import com.world.user_service.role.Role;
import com.world.user_service.role.RoleRepository;
import com.world.user_service.user.User;
import com.world.user_service.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void addEmployee(RegistrationRequest request) {
        Role role = roleRepository.findByName("ROLE_EMPLOYEE")
            .orElseThrow(() -> new IllegalStateException("Role 'EMPLOYEE' has not been initialized."));
        
        User user = User.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .dateOfBirth(request.dateOfBirth())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .roles(List.of(role))
            .enabled(true)
            .build();
        userRepository.save(user);
    }

}
