package com.example.alchemy_app.exception;


public class EntityNotExistException extends RuntimeException {

    public EntityNotExistException(String message) {
        super(message);
    }
}
