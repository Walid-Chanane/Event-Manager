package com.world.event_service.event;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.world.event_service.event.DTOs.AdminEventResponse;
import com.world.event_service.event.DTOs.EventMapper;
import com.world.event_service.event.DTOs.EventRequest;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.event.DTOs.UpdateEventRequest;
import com.world.event_service.event_file.EventFile;
import com.world.event_service.event_file.FileStorageService;
import com.world.event_service.notification.NotificationRequest;
import com.world.event_service.notification.NotificationService;
import com.world.event_service.notification.NotificationType;
import com.world.event_service.pagination.PageResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final FileStorageService fileStorageService;
    private final NotificationService notificationService;

    public Integer add(EventRequest request) {
        Event event = eventMapper.toEvent(request);
        event.setStatus(EventStatus.ACTIVE);
        return eventRepository.save(event).getId();
    }

    public Integer update(UpdateEventRequest request) {
        Event event = eventMapper.updateRequestToEvent(request);
        return eventRepository.save(event).getId();
        
    }

    public EventResponse uploadFiles(Integer eventId, List<MultipartFile> files) {
    
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event " + eventId + " not found!"));

        for (MultipartFile file : files) {
            String filePath = fileStorageService.saveFile(file, event.getId());
            
            String contentType = filePath.contains("images") ? "image" : (filePath.contains("documents") ? "document" : (filePath.contains("videos") ? "video" : "other"));
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

            EventFile savedFile = EventFile.builder()
                    .fileName(file.getOriginalFilename())
                    .filePath(baseUrl + filePath.substring(1))
                    .contentType(contentType)
                    .build();
            
            event.addFile(savedFile);
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

    public void register(Integer eventId, Authentication authenticatedUser, String authHeader) {
        Integer userId = (Integer) authenticatedUser.getPrincipal();
        Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EntityNotFoundException("Event " + eventId + " not found"));

        event.addParticipant(userId);
        eventRepository.save(event);

        NotificationRequest request = NotificationRequest.builder()
            .eventTitle(event.getTitle())
            .type(NotificationType.EVENT_REGISTRATION)
            .userIDs(List.of(userId))
            .build();
        notificationService.sendNotification(request, authHeader);
    }
        
    public void withdraw(Integer eventId, Authentication authenticatedUser, String authHeader) {
        Integer userId = (Integer) authenticatedUser.getPrincipal();
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event " + eventId + " not found"));
            
        event.removeParticipant(userId);
        eventRepository.save(event);
            
            
        NotificationRequest request = NotificationRequest.builder()
            .eventTitle(event.getTitle())
            .type(NotificationType.EVENT_WITHDRAWAL)
            .userIDs(List.of(userId))
            .build();
        notificationService.sendNotification(request, authHeader);
    }

    public PageResponse<AdminEventResponse> getEvents(Pageable pageable) {
        Page<AdminEventResponse> events = eventRepository.findAll(pageable).map(eventMapper::toAdminEventResponse);
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

    public AdminEventResponse getEventById(Integer eventId) {
        return eventRepository.findById(eventId).map(eventMapper::toAdminEventResponse)
            .orElseThrow(() -> new EntityNotFoundException("Event " + eventId + " not found"));
    }

}
