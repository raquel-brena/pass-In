package com.rb.passin.dto.eventDto;

import com.rb.passin.domain.event.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
public class EventResponseDTO {

    EventDetailDTO event;

    public EventResponseDTO (Event event, Integer numberOfAttendees){
        this.event = new EventDetailDTO(
                event.getId(), event.getTitle(),
                event.getDetails(),event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendees);
    }
}
