package com.isw.bookstore.exceptions;

public class BookExistsException extends  BookStoreException{

    public BookExistsException(String message){
        super(message);
    }

    public BookExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
