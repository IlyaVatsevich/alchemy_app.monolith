package com.example.alchemy_app.config;

import com.example.alchemy_app.dto.ErrorResponse;
import com.example.alchemy_app.dto.MultiplyErrorResponse;
import com.example.alchemy_app.exception.EntityNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultiplyErrorResponse handle(ConstraintViolationException e) {

        final Map<String,String> errorMap = new HashMap<>();

        e.getConstraintViolations().forEach(constraintViolation ->
            constraintViolation.getPropertyPath().
                    forEach(node -> errorMap.put(constraintViolation.getMessage(), node.getName())));

        final List<Map<String, String>> errors = errorMap.
                entrySet().
                stream().
                map(entryString -> Map.of("error", entryString.getKey(),"field_name", entryString.getValue())).
                collect(Collectors.toUnmodifiableList());

        return MultiplyErrorResponse.builder().
                withHttpStatus(HttpStatus.BAD_REQUEST).
                withMessages(errors).
                withTimestamp(LocalDateTime.now()).
                build();
    }

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(EntityNotExistException e) {
        return errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST,LocalDateTime.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(IllegalArgumentException e) {
        return errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST,LocalDateTime.now());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(AuthenticationException e) {
        return errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST,LocalDateTime.now());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(ValidationException e) {
        return errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST,LocalDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handle(AccessDeniedException e) {
        return errorResponse(e.getMessage(),HttpStatus.FORBIDDEN,LocalDateTime.now());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(HttpClientErrorException e) {
        return errorResponse(e.getMessage(),HttpStatus.UNAUTHORIZED,LocalDateTime.now());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(IllegalStateException e) {
        return errorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,LocalDateTime.now());
    }

    private ErrorResponse errorResponse(String message,HttpStatus httpStatus,LocalDateTime dateTime) {
        return ErrorResponse.builder().
                withMessage(message).
                withTimestamp(dateTime).
                withHttpStatus(httpStatus).
                build();
    }
}
