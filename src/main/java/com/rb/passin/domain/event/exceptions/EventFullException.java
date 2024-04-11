package com.rb.passin.domain.event.exceptions;

public class EventFullException extends RuntimeException{

    public EventFullException (String msg){
        super(msg);
    }
}
