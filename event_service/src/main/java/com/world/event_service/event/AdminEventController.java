package com.world.event_service.event;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.world.event_service.event.DTOs.AdminEventResponse;
import com.world.event_service.event.DTOs.EventRequest;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.event.DTOs.UpdateEventRequest;
import com.world.event_service.pagination.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminEventController {
    
    private final EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody @Valid EventRequest request){
        return ResponseEntity.ok(eventService.add(request));
    }
    
    @PutMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody @Valid UpdateEventRequest request){
        return ResponseEntity.ok(eventService.update(request));
    }

    @PostMapping(value = "/upload/{eventId}", consumes = "multipart/form-data")
    public ResponseEntity<EventResponse> uploadEventFiles(@PathVariable Integer eventId, @RequestParam("files") List<MultipartFile> files){
        return ResponseEntity.ok(eventService.uploadFiles(eventId, files));
    }

    @PatchMapping("/cancel/{eventId}")
    public ResponseEntity<EventResponse> cancelEvent(@PathVariable Integer eventId){
        return ResponseEntity.ok(eventService.cancelEvent(eventId));
    } 

    @GetMapping("get-events")
    public ResponseEntity<PageResponse<AdminEventResponse>> getEvents(Pageable pageable){
        return ResponseEntity.ok(eventService.getEvents(pageable));
    }

    @GetMapping("get-event/{eventId}")
    public ResponseEntity<AdminEventResponse> getEvent(@PathVariable Integer eventId){
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }
}
