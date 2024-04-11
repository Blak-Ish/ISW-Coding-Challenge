package com.isw.bookstore.exceptions;

public class EmptyCartException extends  BookStoreException{

    public EmptyCartException(String message){
        super(message);
    }

    public EmptyCartException(String message, Throwable cause){
        super(message, cause);
    }
}
