package com.sample.library.authproxy.exception;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
public class AuthException extends RuntimeException{
    public AuthException() {
        super();
    }


    public AuthException(String message) {
        super(message);
    }


    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }


    public AuthException(Throwable cause) {
        super(cause);
    }


    protected AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
