package com.disney.teams.common.exception;

import com.disney.teams.model.Msg;

public class BasicRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1328224619794339672L;

    private String code;

    public BasicRuntimeException() {
        this(StatusCode.SERVER_ERROR);
    }

    public BasicRuntimeException(StatusCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

    public BasicRuntimeException(StatusCode code, Throwable cause) {
        super(code.getMessage(), cause);
        this.code = code.getCode();
    }

    public BasicRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BasicRuntimeException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BasicRuntimeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BasicRuntimeException(Msg msg) {
        this(msg.getCode(), msg.getMsg());
    }

    public Msg msg() {
        return Msg.failed(this.code, super.getMessage());
    }

    public void setCode(StatusCode code) {
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
        return (message != null) ? (s + ": " + message) : s;
    }

}
