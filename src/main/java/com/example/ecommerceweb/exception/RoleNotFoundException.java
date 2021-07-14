package com.example.ecommerceweb.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String roleName){
        super("Could not find role "+ roleName);
    }
}
