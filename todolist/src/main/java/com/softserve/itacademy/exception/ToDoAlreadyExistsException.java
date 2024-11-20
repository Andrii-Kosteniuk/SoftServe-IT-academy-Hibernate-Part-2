package com.softserve.itacademy.exception;

public class ToDoAlreadyExistsException extends RuntimeException{
    public ToDoAlreadyExistsException(String message) {
        super(message);
    }
}
