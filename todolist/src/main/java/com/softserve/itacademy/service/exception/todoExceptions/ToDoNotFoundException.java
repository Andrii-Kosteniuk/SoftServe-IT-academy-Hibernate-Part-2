package com.softserve.itacademy.service.exception.todoExceptions;

public class ToDoNotFoundException extends RuntimeException{
    public ToDoNotFoundException(String message) {
        super(message);
    }
}
