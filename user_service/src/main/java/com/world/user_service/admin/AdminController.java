package com.world.user_service.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.world.user_service.auth.RegistrationRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;

    @PostMapping("add-employee")
    public ResponseEntity<?> addEmployee(@RequestBody @Valid RegistrationRequest request){
        adminService.addEmployee(request);
        return ResponseEntity.ok().build();
    }
}
