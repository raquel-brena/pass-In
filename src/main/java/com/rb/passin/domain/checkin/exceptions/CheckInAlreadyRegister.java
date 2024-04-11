package com.rb.passin.domain.checkin.exceptions;

public class CheckInAlreadyRegister extends RuntimeException{

    public CheckInAlreadyRegister (String msg){
        super(msg);
    }
}
