package com.world.event_service.event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.world.event_service.event.DTOs.EventResponse;
import com.world.event_service.pagination.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    
    @GetMapping("/events/upcoming")
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

@GetMapping("/uploads/event_{eventId}/{type}/{filename}")
public ResponseEntity<Resource> getFile(
        @PathVariable String eventId,
        @PathVariable String filename,
        @PathVariable String type) throws IOException {
            String p = "/home/warch/projects/eventManager/uploads/event_"+ eventId + "/"+ type +"/" + filename;
    Path path = Paths.get(p);

    if (!Files.exists(path)) {
        return ResponseEntity.notFound().build();
    }

    Resource resource = new UrlResource(path.toUri());

    String mimeType = Files.probeContentType(path);
    if (mimeType == null) {
        mimeType = "application/octet-stream";
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(mimeType))
            .body(resource);
}
}
