package com.rb.passin.dto.attendeeDto;

import java.time.LocalDateTime;

public record AttendeeDetails (String id, String name, String email, LocalDateTime checkinAt) {
}
