package com.world.event_service.event.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.world.event_service.event.EventStatus;
import com.world.event_service.event_file.EventFileResponse;

import lombok.Builder;

@Builder
public record AdminEventResponse(
    Integer id,
    String title,
    String description,
    LocalDateTime time,
    String location,
    EventStatus status,
    List<EventFileResponse> files,
    List<Integer> participantIDs
) {}
