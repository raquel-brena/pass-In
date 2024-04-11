package com.rb.passin.service;

import com.rb.passin.domain.attendee.Attendee;
import com.rb.passin.domain.attendee.exceptions.AttendeeAlreadyRegistered;
import com.rb.passin.domain.attendee.exceptions.AttendeeNotFound;
import com.rb.passin.domain.checkin.CheckIn;
import com.rb.passin.dto.attendeeDto.AttendeeBadgeDTO;
import com.rb.passin.dto.attendeeDto.AttendeeDetails;
import com.rb.passin.dto.attendeeDto.AttendeesListResponseDTO;
import com.rb.passin.dto.attendeeDto.BadgeDTO;
import com.rb.passin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent (String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventAttendeeList(String eventId){
        var attendList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendList.stream().map(attendee -> {
                Optional<CheckIn> checkIn = this.checkInService.getCheckin(attendee.getId());
            LocalDateTime checkInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails (attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt());
        }).toList();
        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String eventId, String email){
        Optional<Attendee> attendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if (attendeeRegistered.isPresent()) throw new AttendeeAlreadyRegistered("Attendee is already registered");
    }

    public AttendeeBadgeDTO getAttendeeBadge (String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri();

        BadgeDTO badgeDTO = new BadgeDTO(attendee.getName(), attendee.getName(),uri.getPath(), attendee.getEvent().getId());
        return new AttendeeBadgeDTO(badgeDTO);
    }

    public void registerCheckin (String attendeeId){
        checkInService.registerCheckin(getAttendee(attendeeId));
    }

    private Attendee getAttendee (String attendeeId){
        return this.attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFound("Attendee not found"));
    }
    public void registerAttendee (Attendee attendee) {
        this.attendeeRepository.save(attendee);
    }
}
