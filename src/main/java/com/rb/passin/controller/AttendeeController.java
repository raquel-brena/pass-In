package com.rb.passin.controller;


import com.rb.passin.dto.attendeeDto.AttendeeBadgeDTO;
import com.rb.passin.dto.attendeeDto.AttendeeBadgeResponseDTO;
import com.rb.passin.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @RequestMapping("/{attendeeId}/badges")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge (@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
         AttendeeBadgeDTO response = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(new AttendeeBadgeResponseDTO(response));
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeeService.registerCheckin(attendeeId);

        var uri = uriComponentsBuilder.path("attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
