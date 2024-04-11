package com.rb.passin.domain.attendee.exceptions;

public class AttendeeNotFound extends RuntimeException {
    public AttendeeNotFound (String msg) {
        super(msg);
    }
}
