package com.rb.passin.domain.attendee.exceptions;

public class AttendeeAlreadyRegistered extends RuntimeException{
    public AttendeeAlreadyRegistered (String msg){
        super(msg);
    }
}
