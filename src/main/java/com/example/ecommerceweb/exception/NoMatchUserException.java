package com.example.ecommerceweb.exception;

public class NoMatchUserException extends RuntimeException{
    public NoMatchUserException(String message){
        super(message);
    }
}
