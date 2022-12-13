package com.disney.teams.model.exception;

import com.disney.teams.common.exception.ServiceRuntimeException;
import com.disney.teams.common.exception.StatusCode;
import com.disney.teams.model.Msg;

public class DemoException extends ServiceRuntimeException {
    private static final long serialVersionUID = -1993872348778631465L;

    public DemoException() {
        super(StatusCode.SUCCESS);
    }

    public DemoException(StatusCode statusCode) {
        super(statusCode);
    }

    public DemoException(StatusCode statusCode, Throwable cause) {
        super(statusCode, cause);
    }

    public DemoException(String code, String message) {
        super(code, message);
    }

    public DemoException(String code, Throwable cause) {
        super(code, cause);
    }

    public DemoException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public DemoException(Msg msg) {
        super(msg);
    }
}
