package com.disney.teams.exception;

/**
 * used for common component
 * application should creat a class extend from BasicRuntimeException
 * like class DemoException extends BasicRuntimeException
 */
public class BasicRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1328224619794339672L;

    public static final String COMMON_ERROR_MSG = "common component exception";

    public BasicRuntimeException() {
        this(BasicRuntimeException.COMMON_ERROR_MSG);
    }

    public BasicRuntimeException(String message) {
        super(message);
    }

    public BasicRuntimeException(Throwable throwable) {
        super(BasicRuntimeException.COMMON_ERROR_MSG, throwable);
    }

    public BasicRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
