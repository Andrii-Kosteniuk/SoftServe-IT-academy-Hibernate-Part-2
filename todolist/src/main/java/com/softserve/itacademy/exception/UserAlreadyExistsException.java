package com.softserve.itacademy.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
