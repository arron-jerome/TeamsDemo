package com.disney.teams.exception;

import com.disney.teams.model.vo.BaseStatusCode;

public class ServiceRuntimeException extends BasicRuntimeException {

    private static final long serialVersionUID = 457350410157486450L;

    private String code;


    public ServiceRuntimeException() {
        this(BaseStatusCode.SERVER_ERROR);
    }

    public ServiceRuntimeException(BaseStatusCode statusCode) {
        super(statusCode.getMessage());
        this.code = statusCode.getCode();
    }

    public ServiceRuntimeException(BaseStatusCode statusCode, Throwable cause) {
        super(statusCode.getMessage(), cause);
        this.code = statusCode.getCode();
    }

    public ServiceRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceRuntimeException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public ServiceRuntimeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public void setCode(BaseStatusCode code) {
        this.code = code.getCode();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        String s = getClass().getName();
        String message = this.code + ":" + getLocalizedMessage();
        return s + ": " + message;
    }
}
