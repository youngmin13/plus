package com.assignment.voyage.exception;

import java.io.Serial;

public class NotFoundUserException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -4937536120992271478L;

    public NotFoundUserException() {
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserException(Throwable cause) {
        super(cause);
    }

    public NotFoundUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
