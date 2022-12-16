package com.disney.teamsDemo.model.exception;


import com.disney.teams.exception.ServiceRuntimeException;
import com.disney.teams.model.vo.BaseStatusCode;

public class DemoException extends ServiceRuntimeException {
    public DemoException(String code) {
        super(code);
    }

    public DemoException(StatusCode code) {
        super(code);
    }
}
