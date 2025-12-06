package com.world.user_service.auth;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(

    @NotBlank(message = "This field is required!")
    @Size(min = 3, message = "This field must contain atleast 3 characters!")
    String firstName,
    
    String lastName,
    
    @NotNull
    LocalDate dateOfBirth,
    
    @NotBlank(message = "This field is required!")
    @Email(message = "Incorrect email format provided!")
    String email,
    
    @NotBlank(message = "This field is required!")
    @Size(min = 8, message = "This field must contain atleast 8 characters!")
    String password
) {}
