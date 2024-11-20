package com.softserve.itacademy.utilty;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;

import java.time.LocalDateTime;

public class DataVerification {

    public static void checkWhetherToDoIsValidOrNot(ToDo toDo) {
        if (toDo.getTitle().isBlank()) throw new IllegalArgumentException("Title cannot be blank");
        if (toDo.getOwner() == null) throw new IllegalArgumentException("Owner cannot be null");
        if (toDo.getCreatedAt().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("CreatedAt cannot be in the future");
    }

    public static void checkUserIsValidOrNot(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be 'null'");
        if (user.getFirstName().isBlank()) throw new IllegalArgumentException("User first name cannot be empty");
        if (user.getLastName().isBlank()) throw new IllegalArgumentException("User last name cannot be empty");
        if (user.getRole() == null) throw new IllegalArgumentException("User role can not be null");

        if (! user.getEmail().matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}"))
            throw new IllegalArgumentException("Must be a valid e-mail address");

        if (! user.getPassword().matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=]{8,}")) {
            throw new IllegalArgumentException("Must be a valid password");
        }


    }

}