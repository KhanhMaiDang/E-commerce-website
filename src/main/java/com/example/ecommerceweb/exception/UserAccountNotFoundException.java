package com.example.ecommerceweb.exception;

public class UserAccountNotFoundException extends RuntimeException{
    public UserAccountNotFoundException(Long accId){
        super("Can not find account with id "+ accId);
    }
}
