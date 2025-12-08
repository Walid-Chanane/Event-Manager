package com.world.event_service.event.DTOs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.world.event_service.event.Event;
import com.world.event_service.event_file.EventFileMapper;
import com.world.event_service.event_file.EventFileResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventMapper {

    private final EventFileMapper eventFileMapper;

    public Event toEvent(EventRequest request){
        return Event.builder()
            .id(request.id())
            .title(request.title())
            .description(request.description())
            .time(request.time())
            .location(request.location())
            .build();
    }

    public EventResponse toEventResponse(Event event){
        List<EventFileResponse> eventFiles = new ArrayList<EventFileResponse>();
        if (event.getFiles() != null) eventFiles = event.getFiles().stream().map(eventFileMapper::toResponse).toList();
        
        return EventResponse.builder()
            .id(event.getId())
            .title(event.getTitle())
            .description(event.getDescription())
            .time(event.getTime())
            .location(event.getLocation())
            .status(event.getStatus())
            .files(eventFiles)
            .build();
    }

}
