package com.rb.passin.controller;

import com.rb.passin.dto.attendeeDto.AttendeeBadgeResponseDTO;
import com.rb.passin.dto.attendeeDto.AttendeeRequestDTO;
import com.rb.passin.dto.attendeeDto.AttendeesListResponseDTO;
import com.rb.passin.dto.eventDto.EventIdDTO;
import com.rb.passin.dto.eventDto.EventRequestDTO;
import com.rb.passin.dto.eventDto.EventResponseDTO;
import com.rb.passin.service.AttendeeService;
import com.rb.passin.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent (@RequestBody EventRequestDTO event, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventId = this.eventService.createEvent(event);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventId.id()).toUri();
        return ResponseEntity.created(uri).body(eventId);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<EventIdDTO> registerParticipant (@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);
        var uri = uriComponentsBuilder.path("/events/{id}/badge").buildAndExpand(eventIdDTO.id()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String eventId) {
        AttendeesListResponseDTO attendeeList = this.attendeeService.getEventAttendeeList(eventId);
        return ResponseEntity.ok(attendeeList);
    }


}
