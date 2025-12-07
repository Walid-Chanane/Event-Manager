package com.world.event_service.event;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.world.event_service.event.DTOs.EventRequest;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.pagination.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    
    @PostMapping("/save")
    public ResponseEntity<EventResponse> save(@RequestBody @Valid EventRequest request){
        EventResponse event = eventService.save(request);
        URI uri = URI.create("/events/" + event.id());
        return ResponseEntity.created(uri).body(event);
    }

    @PostMapping(value = "/upload/{eventId}", consumes = "multipart/form-data")
    public ResponseEntity<EventResponse> uploadEventFiles(@PathVariable Integer eventId, @RequestParam("files") List<MultipartFile> files){
        return ResponseEntity.ok(eventService.uploadFiles(eventId, files));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<PageResponse<EventResponse>> getUpcomingEvents(Pageable pageable
    ){
        return ResponseEntity.ok(eventService.getUpcomingEvents(pageable));
    }
}
