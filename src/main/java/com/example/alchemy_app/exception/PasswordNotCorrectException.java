package com.example.alchemy_app.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordNotCorrectException extends AuthenticationException {

    public PasswordNotCorrectException(String message) {
        super(message);
    }

}
