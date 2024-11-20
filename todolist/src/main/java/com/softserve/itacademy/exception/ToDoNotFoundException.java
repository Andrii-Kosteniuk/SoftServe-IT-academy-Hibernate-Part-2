package com.softserve.itacademy.exception;

public class ToDoNotFoundException extends RuntimeException{
    public ToDoNotFoundException(String message) {
        super(message);
    }
}
