package com.rb.passin.controller;

import com.rb.passin.dto.eventDto.EventIdDTO;
import com.rb.passin.dto.eventDto.EventRequestDTO;
import com.rb.passin.dto.eventDto.EventResponseDTO;
import com.rb.passin.service.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO event = this.service.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent (@RequestBody EventRequestDTO event, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventId = this.service.createEvent(event);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventId.id()).toUri();
        return ResponseEntity.created(uri).body(eventId);
    }

}
