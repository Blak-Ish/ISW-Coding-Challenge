package com.isw.bookstore.exceptions;

public class ValidationException extends BookStoreException {

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(String message, Throwable cause){
        super(message, cause);
    }
}