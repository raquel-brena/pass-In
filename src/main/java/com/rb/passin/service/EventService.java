package com.rb.passin.service;

import com.rb.passin.domain.attendee.Attendee;
import com.rb.passin.domain.event.Event;
import com.rb.passin.domain.event.exceptions.EventFullException;
import com.rb.passin.domain.event.exceptions.EventNotFoundException;
import com.rb.passin.dto.attendeeDto.AttendeeRequestDTO;
import com.rb.passin.dto.eventDto.EventIdDTO;
import com.rb.passin.dto.eventDto.EventRequestDTO;
import com.rb.passin.dto.eventDto.EventResponseDTO;
import com.rb.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId) {
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent (EventRequestDTO event) {
        Event newEvent = new Event();
        newEvent.setTitle(event.title());
        newEvent.setDetails(event.title());
        newEvent.setMaximumAttendees(event.maximumAttendees());
        newEvent.setSlug(this.createSlug(event.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    public EventIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendee){
        this.attendeeService.verifyAttendeeSubscription(eventId, attendee.email());

        Event event = getEventById(eventId);

        List<Attendee> attendeeList = attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size())
            throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendee.nome());
        newAttendee.setEmail(attendee.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new EventIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID:" + eventId));
    }
    private String createSlug (String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS_EXTENDED}]", "")
                .replaceAll("[^\\w\\s]","")
                .replaceAll("[\\s+]","-")
                .toLowerCase();
    }
}
