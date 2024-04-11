package com.isw.bookstore.exceptions;

public class BookStoreException extends RuntimeException{

    BookStoreException(String message){
        super(message);
    }

    BookStoreException(String message, Throwable cause){
        super(message, cause);
        if(this.getCause() == null && cause != null){
            this.initCause(cause);
        }
    }
}