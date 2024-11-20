package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.ToDoAlreadyExistsException;
import com.softserve.itacademy.exception.ToDoNotFoundException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;

import com.softserve.itacademy.utilty.DataVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ToDoService interface.
 * Provides business logic for managing ToDo tasks, including creating, updating, deleting, and retrieving tasks.
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
        DataVerification.checkWhetherToDoIsValidOrNot(todo);

        if (repository.existsById(todo.getId())) {
            String errorMessage = "Todo with ID " + todo.getId() + " already exists in the database.";
            logger.info(errorMessage);
            throw new ToDoAlreadyExistsException(errorMessage);
        }

        ToDo savedTodo = repository.save(todo);
        logger.info("Todo " + savedTodo.getTitle() + " was saved to the database");
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
        DataVerification.checkWhetherToDoIsValidOrNot(todo);

        ToDo toDo = readById(todo.getId());

        repository.save(toDo);
        logger.log(Level.INFO, "Todo " + toDo.getTitle() + " was updated successfully");
        return toDo;
    }

    @Override
    public void delete(long id) {
        readById(id);
        repository.deleteById(id);
        logger.log(Level.INFO, "Todo with ID " + id + " was successfully deleted from the database");
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
