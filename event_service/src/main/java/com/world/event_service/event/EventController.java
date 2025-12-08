package com.world.event_service.event;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.pagination.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    
    @GetMapping("/upcoming")
    public ResponseEntity<PageResponse<EventResponse>> getUpcomingEvents(Pageable pageable){
        return ResponseEntity.ok(eventService.getUpcomingEvents(pageable));
    }
    
    @PatchMapping("/{eventId}/register")
    public ResponseEntity<?> register(@PathVariable Integer eventId, Authentication authenticatedUser, @RequestHeader("Authorization") String authHeader){
        eventService.register(eventId, authenticatedUser, authHeader);
        return ResponseEntity.ok().build();
    }    
    
    @PatchMapping("/{eventId}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Integer eventId, Authentication authenticatedUser, @RequestHeader("Authorization") String authHeader){
        eventService.withdraw(eventId, authenticatedUser, authHeader);
        return ResponseEntity.ok().build();
    }
}
