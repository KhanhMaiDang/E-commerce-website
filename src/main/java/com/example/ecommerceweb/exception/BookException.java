package com.example.ecommerceweb.exception;

public class BookException extends  RuntimeException{
    public BookException(Long id){
        super("Could not find category with id: "+ id);
    }
}
