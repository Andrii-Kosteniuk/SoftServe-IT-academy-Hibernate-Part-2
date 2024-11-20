package com.softserve.itacademy.service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.exception.ToDoAlreadyExistsException;
import com.softserve.itacademy.exception.ToDoNotFoundException;

import java.util.List;

/**
 * Service interface for managing ToDo tasks.
 * Contains methods for creating, reading, updating, and deleting ToDo tasks.
 */
public interface ToDoService {
    /**
     * Creates a new ToDo task.
     *
     * @param todo the ToDo task to create
     * @return the created ToDo task
     *
     * @throws ToDoAlreadyExistsException if a ToDo with the same ID already exists
     */
    ToDo create(ToDo todo);

    /**
     * Retrieves a ToDo task by its ID.
     *
     * @param id the ID of the ToDo task
     * @return the ToDo task with the given ID
     *
     * @throws ToDoNotFoundException if no ToDo is found with the given ID
     */
    ToDo readById(long id);

    /**
     * Updates an existing ToDo task.
     *
     * @param todo the ToDo task with updated details
     * @return the updated ToDo task
     *
     * @throws ToDoNotFoundException if the ToDo with the given ID does not exist
     */
    ToDo update(ToDo todo);

    /**
     * Deletes a ToDo task by its ID.
     *
     * @param id the ID of the ToDo task to delete
     * @throws ToDoNotFoundException if no ToDo is found with the given ID
     */
    void delete(long id);

    /**
     * Retrieves all ToDo tasks that belong to a specific user.
     *
     * @param userId the ID of the user
     * @return a list of ToDo tasks where the user is either the owner or a collaborator
     */
    List<ToDo> getByUserId(long userId);

    /**
     * Retrieves all ToDo tasks.
     *
     * @return a list of all ToDo tasks
     */
    List<ToDo> getAll();
}
