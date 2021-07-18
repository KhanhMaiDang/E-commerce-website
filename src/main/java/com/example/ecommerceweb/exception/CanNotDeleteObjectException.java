package com.example.ecommerceweb.exception;

public class CanNotDeleteObjectException extends RuntimeException{
    public CanNotDeleteObjectException(){
        super("Can not delete this object");
    }
}
