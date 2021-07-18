package com.example.ecommerceweb.exception;

public class CanNotUploadImageException extends RuntimeException{
    public CanNotUploadImageException(){
        super("Can not up load image");
    }
}
