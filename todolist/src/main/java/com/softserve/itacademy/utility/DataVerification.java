package com.softserve.itacademy.utility;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;

import java.time.LocalDateTime;

public class DataVerification {

    public static void checkWhetherToDoIsValidOrNot(ToDo toDo) {
        if (toDo.getTitle().isBlank()) throw new IllegalArgumentException("Invalid ToDo data: title cannot be blank");
        if (toDo.getCreatedAt().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Invalid ToDo data: createdAt cannot be in the future or null");
        if (toDo.getOwner() == null) throw new IllegalArgumentException("Invalid ToDo data: owner cannot be null");
    }

    public static void checkUserIsValidOrNot(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be 'null'");
        if (user.getFirstName().isBlank()) throw new IllegalArgumentException("User first name cannot be empty");
        if (user.getLastName().isBlank()) throw new IllegalArgumentException("User last name cannot be empty");
        if (!user.getEmail().matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}"))
            throw new IllegalArgumentException("Must be a valid e-mail addres");
        if (user.getRole() == null) throw new IllegalArgumentException("User role can not be null");
        if (!user.getPassword().matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=]{8,}"))
            throw new IllegalArgumentException("Must be a valid password");
    }

}
