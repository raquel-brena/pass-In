package com.rb.passin.service;

import com.rb.passin.domain.event.Event;
import com.rb.passin.dto.eventDto.EventIdDTO;
import com.rb.passin.dto.eventDto.EventRequestDTO;
import com.rb.passin.dto.eventDto.EventResponseDTO;
import com.rb.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponseDTO getEventDetail(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("teste"));
        return new EventResponseDTO(event, 0);
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

    private String createSlug (String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS_EXTENDED}]", "")
                .replaceAll("[^\\w\\s]","")
                .replaceAll("[\\s+]","-")
                .toLowerCase();
    }
}
