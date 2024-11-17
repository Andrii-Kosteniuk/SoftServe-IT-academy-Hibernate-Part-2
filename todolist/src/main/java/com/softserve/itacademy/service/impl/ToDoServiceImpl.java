package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.exception.todoExceptions.ToDoAlreadyExistsException;
import com.softserve.itacademy.service.exception.todoExceptions.ToDoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ToDoService interface.
 * Provides business logic for managing ToDo tasks, including creating, updating, deleting,
 * and retrieving tasks.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repository;
    private final Logger logger = Logger.getLogger(ToDoServiceImpl.class.getName());

    @Override
    public ToDo create(ToDo todo) {
        Objects.requireNonNull(todo, "Todo can not be a null");

        if (todo.getId() != 0 && repository.existsById(todo.getId())) {
            String errorMessage = "Todo with ID " + todo.getId() + " already exists in the database.";
            logger.log(Level.WARNING, errorMessage);
            throw new ToDoAlreadyExistsException(errorMessage);
        }

        ToDo savedTodo = repository.save(todo);
        logger.log(Level.INFO, "Todo '{}' was saved to the database", savedTodo.getTitle());
        return savedTodo;
    }

    @Override
    public ToDo readById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ToDoNotFoundException("Todo with ID " + id + " was not found"));
    }

    @Override
    public ToDo update(ToDo todo) {
        Objects.requireNonNull(todo, "Todo cannot be null");

        if (! repository.existsById(todo.getId())) {
            String errorMessage = "Todo with ID " + todo.getId() + " was not found in the database.";
            logger.log(Level.WARNING, errorMessage);
            throw new ToDoNotFoundException(errorMessage);
        }

        ToDo updatedTodo = repository.save(todo);
        logger.log(Level.INFO, "Todo '{}' was updated successfully", updatedTodo.getTitle());
        return updatedTodo;
    }

    @Override
    public void delete(long id) {
        if (! repository.existsById(id)) {
            String errorMessage = "Todo with ID " + id + " was not found in the database.";
            logger.log(Level.WARNING, errorMessage);
            throw new ToDoNotFoundException(errorMessage);
        }

        repository.deleteById(id);
        logger.log(Level.INFO, "Todo with ID '{}' was successfully deleted from the database", id);
    }

    @Override
    public List<ToDo> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        return repository.getAllToDoByUserId(userId);
    }
}
