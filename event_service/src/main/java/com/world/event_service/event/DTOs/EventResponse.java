package com.world.event_service.event.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.world.event_service.event_file.EventFileResponse;

import lombok.Builder;

@Builder
public record EventResponse(

    Integer id,
    String title,
    String description,
    LocalDateTime time,
    String location,
    List<EventFileResponse> files

) {}
