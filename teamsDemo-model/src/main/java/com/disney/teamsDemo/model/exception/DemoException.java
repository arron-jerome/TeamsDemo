package com.disney.teamsDemo.model.exception;


import com.disney.teams.exception.ServiceRuntimeException;

public class DemoException extends ServiceRuntimeException {

    public DemoException(StatusCode code) {
        super(code);
    }
}
