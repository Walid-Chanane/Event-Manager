package com.world.event_service.event;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.world.event_service.event.DTOs.AdminEventResponse;
import com.world.event_service.event.DTOs.EventRequest;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.event.DTOs.UpdateEventRequest;
import com.world.event_service.pagination.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Tag(name = "Event Admin")
public class AdminEventController {
    
    private final EventService eventService;

    @Operation(summary = "Create a new event")
    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody @Valid EventRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.add(request));
    }
    
    @Operation(summary = "Update existing event")
    @PutMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody @Valid UpdateEventRequest request){
        return ResponseEntity.ok(eventService.update(request));
    }

    @Operation(summary = "Upload event files")
    @PostMapping(value = "/upload/{eventId}", consumes = "multipart/form-data")
    public ResponseEntity<EventResponse> uploadEventFiles(@PathVariable Integer eventId,
                    @Parameter(description = "Type of file: image or video")
                    @RequestParam("files") List<MultipartFile> files){
        return ResponseEntity.ok(eventService.uploadFiles(eventId, files));
    }

    @Operation(summary = "Cancel event")
    @PatchMapping("/cancel/{eventId}")
    public ResponseEntity<EventResponse> cancelEvent(@PathVariable Integer eventId){
        return ResponseEntity.ok(eventService.cancelEvent(eventId));
    } 

    @Operation(summary = "Get all events")
    @GetMapping("get-events")
    public ResponseEntity<PageResponse<AdminEventResponse>> getEvents(Pageable pageable){
        return ResponseEntity.ok(eventService.getEvents(pageable));
    }

    @Operation(summary = "Get event by ID")
    @GetMapping("get-event/{eventId}")
    public ResponseEntity<AdminEventResponse> getEvent(@PathVariable Integer eventId){
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @Operation(summary = "Remove participant from event")
    @PatchMapping("remove/{eventId}/{participantId}")
    public ResponseEntity<Void> removeParticipantFromEvent(@PathVariable Integer eventId, @PathVariable Integer participantId, @RequestHeader("Authorization") String authHeader){
        eventService.removeFromEvent(eventId, participantId, authHeader);
        return ResponseEntity.noContent().build();
    }
}
