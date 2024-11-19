package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT t FROM Task t LEFT JOIN FETCH ToDo td ON td.id = t.todo.id WHERE td.id = :id")
	List<Task> findAllByTodo_Id(long id);
}
