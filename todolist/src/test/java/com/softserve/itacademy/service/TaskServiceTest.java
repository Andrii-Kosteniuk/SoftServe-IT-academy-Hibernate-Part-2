package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.TaskPriority;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskServiceImpl taskService;

	private Task expectedTask;


	@BeforeEach
	void setUp() {
		expectedTask = new Task();
		expectedTask.setId(1L);
		expectedTask.setState(new State());
		expectedTask.setName("Task 1");
		expectedTask.setTodo(new ToDo());
		expectedTask.setPriority(TaskPriority.HIGH);
	}

	@AfterEach
	void clear() {
		expectedTask = null;
	}

	@Test
	void shouldSaveTask() {
		when(taskRepository.save(expectedTask)).thenReturn(expectedTask);
		Task task = taskService.create(expectedTask);
		assertEquals(expectedTask, task);
		verify(taskRepository, times(1)).save(expectedTask);
	}

	@Test
	void shouldThrowExceptionIfTaskAlreadyExists() {
		when(taskRepository.findById(expectedTask.getId())).thenReturn(Optional.ofNullable(expectedTask));
		Exception exception = assertThrows(EntityExistsException.class, () -> taskService.create(expectedTask));
		assertEquals("Task with ID 1 already exist", exception.getMessage());
		verify(taskRepository, never()).save(expectedTask);
	}

	@Test
	void shouldReadTaskById() {
		when(taskRepository.findById(expectedTask.getId())).thenReturn(Optional.ofNullable(expectedTask));
		Task task = taskService.readById(expectedTask.getId());
		assertEquals(expectedTask, task);
		verify(taskRepository, times(1)).findById(expectedTask.getId());
	}

	@Test
	void shouldThrowExceptionIfTaskNotFound() {
		when(taskRepository.findById(expectedTask.getId())).thenReturn(Optional.empty());
		Exception exception = assertThrows(EntityNotFoundException.class, () -> taskService.readById(expectedTask.getId()));
		assertEquals("Task with ID 1 not found", exception.getMessage());
		verify(taskRepository, times(1)).findById(expectedTask.getId());
	}

	@Test
	void shouldUpdateTask() {
		when(taskRepository.existsById(expectedTask.getId())).thenReturn(true);
		when(taskRepository.save(expectedTask)).thenReturn(expectedTask);
		Task task = taskService.update(expectedTask);
		assertEquals(expectedTask, task);
		verify(taskRepository, times(1)).existsById(expectedTask.getId());
		verify(taskRepository, times(1)).save(expectedTask);
	}

	@Test
	void shouldCheckTaskCanNotBeNull() {
		Exception exception = assertThrows(NullPointerException.class, () -> taskService.update(null));
		assertEquals("Task cannot be null", exception.getMessage());
		verify(taskRepository, never()).existsById(null);
	}

	@Test
	void shouldDeleteTask() {
		when(taskRepository.existsById(expectedTask.getId())).thenReturn(true);
		doNothing().when(taskRepository).deleteById(expectedTask.getId());
		taskService.delete(expectedTask.getId());
		verify(taskRepository, times(1)).existsById(expectedTask.getId());
		verify(taskRepository, times(1)).deleteById(expectedTask.getId());
	}

	@Test
	void shouldReturnAllTasks() {
		List<Task> expectedResult = List.of(new Task(), new Task(), new Task(), new Task());
		when(taskRepository.findAll()).thenReturn(expectedResult);
		List<Task> tasks = taskService.getAll();
		assertIterableEquals(expectedResult, tasks);
		verify(taskRepository, times(1)).findAll();
	}

	@Test
	void shouldReturnTaskByTodo() {
		List<Task> expectedResult = List.of(new Task(), new Task(), new Task(), new Task());
		when(taskRepository.findAllByTodo_Id(anyLong())).thenReturn(expectedResult);
		List<Task> tasks = taskRepository.findAllByTodo_Id(anyLong());
		assertIterableEquals(expectedResult, tasks);
		verify(taskRepository, times(1)).findAllByTodo_Id(anyLong());
	}
}
