package com.rb.passin.domain.event.exceptions;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException (String msg){
        super(msg);
    }
}
