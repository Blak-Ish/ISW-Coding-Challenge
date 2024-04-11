package com.isw.bookstore.exceptions;

public class NotFoundException extends BookStoreException {

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}