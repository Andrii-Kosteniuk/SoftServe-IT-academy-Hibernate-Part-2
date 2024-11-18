package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on ToDo entities.
 * Extends JpaRepository for basic CRUD functionality.
 * <p>
 * Includes a custom query to fetch ToDo tasks based on user ownership or collaboration.
 */

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
// Implement a method that retrieves a list of ToDo objects where the user is either the owner
// or a collaborator. The method should accept a long value representing the userId as a parameter.
// It should return all tasks from the 'todos' table where the user is the owner (owner_id) or
// a collaborator (collaborator_id) in the 'todo_collaborator' table.

    /**
     * Retrieves a list of ToDo objects where the user is either the owner or a collaborator.
     *
     * @param userId the ID of the user
     * @return a list of ToDo objects where the user is either the owner or a collaborator
     */

    @Query(value = "SELECT t FROM ToDo t LEFT JOIN t.collaborators  c WHERE t.owner.id = :userId OR c.id = :userId")
    List<ToDo> getAllToDoByUserId(@Param("userId") long userId);

}
