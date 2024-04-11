package com.rb.passin.dto.attendeeDto;

import lombok.Getter;

import java.util.List;

public record  AttendeesListResponseDTO (List<AttendeeDetails> attendees){
}
