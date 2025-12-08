package com.world.user_service.admin;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record UserResponse(
    Integer id,
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String email
) {}
