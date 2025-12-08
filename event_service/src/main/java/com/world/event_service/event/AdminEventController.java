package com.world.event_service.event;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.world.event_service.event.DTOs.AdminEventResponse;
import com.world.event_service.event.DTOs.EventRequest;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.pagination.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/event")
@RequiredArgsConstructor
public class AdminEventController {
    
    private final EventService eventService;

    @PostMapping("/save")
    public ResponseEntity<EventResponse> save(@RequestBody @Valid EventRequest request){
        EventResponse event = eventService.save(request);
        URI uri = URI.create("/admin/event/" + event.id());
        return ResponseEntity.created(uri).body(event);
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

}
