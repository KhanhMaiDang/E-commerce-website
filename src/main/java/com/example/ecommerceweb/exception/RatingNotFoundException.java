package com.example.ecommerceweb.exception;

public class RatingNotFoundException extends RuntimeException{

    public RatingNotFoundException(Long id){
        super("Could not find rating with id "+ id);
    }
}

