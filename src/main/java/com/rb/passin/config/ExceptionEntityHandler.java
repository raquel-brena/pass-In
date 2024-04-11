package com.rb.passin.config;


import com.rb.passin.domain.attendee.exceptions.AttendeeNotFound;
import com.rb.passin.domain.event.exceptions.EventNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound (EventNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFound.class)
    public ResponseEntity handleAttendeeNotFound (AttendeeNotFound e){
        return ResponseEntity.notFound().build();
    }
}
