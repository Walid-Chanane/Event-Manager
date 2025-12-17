package com.world.event_service.event.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.world.event_service.event.EventStatus;
import com.world.event_service.event_file.EventFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEventRequest(

    Integer id,

    @NotBlank(message = "This field is required!")
    String title,
    
    @NotBlank(message = "This field is required!")
    String description,

    @NotNull(message = "This field is required!")
    LocalDateTime time,
    
    @NotBlank(message = "This field is required!")
    String location,

    @NotNull
    EventStatus status,

    List<EventFile> files,
    
    List<Integer> participantIDs


) {}
