package com.world.event_service.event.DTOs;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventRequest(

    Integer id,

    @NotBlank(message = "This field is required!")
    String title,
    
    @NotBlank(message = "This field is required!")
    String description,

    @NotNull(message = "This field is required!")
    LocalDateTime time,
    
    @NotBlank(message = "This field is required!")
    String location

) {}
