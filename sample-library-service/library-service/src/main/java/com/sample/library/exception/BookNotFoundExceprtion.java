package com.sample.library.exception;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
public class BookNotFoundExceprtion extends RuntimeException {
    public BookNotFoundExceprtion() {
        super();
    }


    public BookNotFoundExceprtion(String message) {
        super(message);
    }


    public BookNotFoundExceprtion(String message, Throwable cause) {
        super(message, cause);
    }


    public BookNotFoundExceprtion(Throwable cause) {
        super(cause);
    }


    protected BookNotFoundExceprtion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
