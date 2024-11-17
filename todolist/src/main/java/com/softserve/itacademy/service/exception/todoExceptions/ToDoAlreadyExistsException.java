package com.softserve.itacademy.service.exception.todoExceptions;

public class ToDoAlreadyExistsException extends RuntimeException{
    public ToDoAlreadyExistsException(String message) {
        super(message);
    }
}
