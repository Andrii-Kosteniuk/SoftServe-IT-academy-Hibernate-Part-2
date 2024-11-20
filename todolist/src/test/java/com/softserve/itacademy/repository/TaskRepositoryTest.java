package com.softserve.itacademy.repository;


import com.softserve.itacademy.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
		"spring.test.database.replace=NONE",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.url=jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.sql.init.mode=never"
})
class TaskRepositoryTest {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ToDoRepository toDoRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private UserRepository userRepository;

	private ToDo toDo;



	@BeforeEach
	void setUp() {
		toDo = new ToDo();
		State state = new State();
		User user = new User();

		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("john@doe.com");
		user.setPassword("Qwerty1234!");
		user.setRole(UserRole.USER);
		userRepository.save(user);

		state.setName("New");
		stateRepository.save(state);

		toDo.setTitle("TODO List");
		toDo.setCreatedAt(LocalDateTime.now());
		toDo.setOwner(user);
		toDoRepository.save(toDo);

		Task task1 = new Task();
		task1.setName("Task 1");
		task1.setPriority(TaskPriority.HIGH);
		task1.setState(state);
		task1.setTodo(toDo);
		taskRepository.save(task1);

		Task task2 = new Task();
		task2.setName("Task 2");
		task2.setPriority(TaskPriority.HIGH);
		task2.setState(state);
		task2.setTodo(toDo);
		taskRepository.save(task2);
	}

	@Test
	void shouldReturnAllTasksByTodoId() {
		List<Task> result = taskRepository.findAllByTodo_Id(toDo.getId());
		assertThat(result).hasSize(2);
		assertThat(result).extracting(Task::getTodo).allMatch(todo -> todo.equals(toDo));
	}

	@Test
	void shouldMismatchTasksByTodoId() {
		List<Task> result = taskRepository.findAllByTodo_Id(-1l);
		assertThat(result).isEmpty();
	}

}
