package com.world.event_service.event;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.world.event_service.event.DTOs.EventMapper;
import com.world.event_service.event.DTOs.EventRequest;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.event_file.EventFile;
import com.world.event_service.event_file.FileStorageService;
import com.world.event_service.pagination.PageResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final FileStorageService fileStorageService;

    public EventResponse save(EventRequest request) {
        Event event = eventMapper.toEvent(request);
        event.setStatus(EventStatus.ACTIVE);
        event = eventRepository.save(event);
        return eventMapper.toEventResponse(event);
    }

    public EventResponse uploadFiles(Integer eventId, List<MultipartFile> files) {
    
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event " + eventId + " not found!"));

        for (MultipartFile file : files) {
            String filePath = fileStorageService.saveFile(file, event.getId());
            
            String contentType = filePath.contains("images") ? "image" : (filePath.contains("documents") ? "document" : (filePath.contains("videos") ? "video" : "other"));
            
            EventFile savedFile = EventFile.builder()
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath)
                    .contentType(contentType)
                    .build();
            
            event.add(savedFile);
        }
        
        event = eventRepository.save(event);
        return eventMapper.toEventResponse(event);
    }

    public PageResponse<EventResponse> getUpcomingEvents(Pageable pageable) {
        Page<EventResponse> events = eventRepository.findByStatusAndTimeAfter(EventStatus.ACTIVE, LocalDateTime.now(), pageable).map(eventMapper::toEventResponse);
        return new PageResponse<>(
            events.getContent(),
            events.getNumber(),
            events.getSize(),
            events.getTotalElements(),
            events.getTotalPages(),
            events.isFirst(),
            events.isLast()
        );
    }

    public EventResponse cancelEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event " + eventId + " not found"));
        event.setStatus(EventStatus.CANCELLED);
        event = eventRepository.save(event);
        return eventMapper.toEventResponse(event);
    }

}
