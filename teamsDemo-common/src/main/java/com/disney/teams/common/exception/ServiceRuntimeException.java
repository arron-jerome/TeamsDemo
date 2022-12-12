package com.disney.teams.common.exception;

import com.disney.teams.model.Msg;

public class ServiceRuntimeException extends BasicRuntimeException {
    private static final String DEFAULT_SERVICE_CODE = StatusCode.SERVER_ERROR_CODE;

    private static final long serialVersionUID = 457350410157486450L;


    public ServiceRuntimeException() {
        super();
    }

    public ServiceRuntimeException(String message) {
        super(DEFAULT_SERVICE_CODE, message);
    }

    public ServiceRuntimeException(StatusCode code) {
        super(code);
    }

    public ServiceRuntimeException(StatusCode code, Throwable cause) {
        super(code, cause);
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(DEFAULT_SERVICE_CODE, message, cause);
    }

    public ServiceRuntimeException(Throwable cause) {
        super(DEFAULT_SERVICE_CODE, cause);
    }

    public ServiceRuntimeException(String code, String message) {
        super(code, message);
    }

    public ServiceRuntimeException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ServiceRuntimeException(Msg msg) {
        super(msg == null ? DEFAULT_SERVICE_CODE : msg.getCode(), msg == null ? StatusCode.SERVER_ERROR_MSG : msg.getMsg());
    }
}
