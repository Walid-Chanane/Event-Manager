package com.world.user_service.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.world.user_service.auth.RegistrationRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Tag(name = "User Admin")
public class AdminController {
    
    private final AdminService adminService;

    @Operation(summary = "Create employee account")
    @PostMapping("add-employee")
    public ResponseEntity<Void> addEmployee(@RequestBody @Valid RegistrationRequest request){
        adminService.addEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get users by ID list")
    @PostMapping("users/by-id")
    public ResponseEntity<List<UserResponse>> getUsers(@RequestBody List<Integer> userIDs){
        return ResponseEntity.ok(adminService.getUsers(userIDs));
    }
}
