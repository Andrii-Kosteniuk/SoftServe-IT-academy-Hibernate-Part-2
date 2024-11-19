package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;

	private static final String NOT_NULL = "Task cannot be null";
	private static final String NOT_FOUND = "Task with ID %s not found";
	private static final String FOUND = "Task with ID %s already exist";

	@Override
	public Task create(Task task) {
		Objects.requireNonNull(task, NOT_NULL);
		taskRepository.findById(task.getId())
				.ifPresent(exist -> {
							throw new EntityExistsException(FOUND.formatted(task.getId()));
						});
		return taskRepository.save(task);
	}

	@Override
	public Task readById(long id) {
		return taskRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
	}

	@Override
	public Task update(Task task) {
		Objects.requireNonNull(task, NOT_NULL);
		if (taskRepository.existsById(task.getId())) {
			return taskRepository.save(task);
		}
		throw new EntityNotFoundException(NOT_FOUND.formatted(task.getId()));
	}

	@Override
	public void delete(long id) {
		if (taskRepository.existsById(id)) {
			taskRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException(NOT_FOUND.formatted(id));
		}
	}

	@Override
	public List<Task> getAll() {
		return taskRepository.findAll();
	}

	@Override
	public List<Task> getByTodoId(long todoId) {
		return taskRepository.findAllByTodo_Id(todoId);
	}
}
