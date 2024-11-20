package com.softserve.itacademy.service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.utilty.DataVerification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;
    @InjectMocks
    private ToDoServiceImpl toDoService;

    private ToDo expectedToDo;
    private User user;

    private static Stream<ToDo> provideInvalidToDoData() {
        User user = new User();
        return Stream.of(
                new ToDo(" ", user, LocalDateTime.now()),
                new ToDo("Title-1", user, LocalDateTime.now().plusDays(1)),
                new ToDo("Title-2", null, LocalDateTime.now())
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidToDoData")
    void testCheckWhetherToDoIsValidOrNotWithInvalidData(ToDo invalidToDo) {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataVerification.checkWhetherToDoIsValidOrNot(invalidToDo),
                "Expected IllegalArgumentException for invalid ToDo"
        );
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFirstName("Mike");
        user.setLastName("Green");
        user.setEmail("mike@mail.com");
        user.setPassword("Qwerty1!");

        expectedToDo = new ToDo();
        expectedToDo.setTitle("Title-1");
        expectedToDo.setCreatedAt(LocalDateTime.now());
        expectedToDo.setOwner(user);
        expectedToDo.setTasks(new ArrayList<>());
        expectedToDo.setCollaborators(new ArrayList<>());
    }

    @Test
    void testCorrectCreate() {
        // Given
        when(toDoRepository.save(expectedToDo)).thenReturn(expectedToDo);

        // When
        ToDo actual = toDoService.create(expectedToDo);

        // Assert
        assertEquals(expectedToDo, actual);
        assertEquals(actual.getTitle(), "Title-1");

        verify(toDoRepository, times(1)).save(expectedToDo);
    }

    @Test
    void testCorrectReadById() {
        // Given
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(expectedToDo));

        // When
        ToDo actual = toDoService.readById(anyLong());

        // Assert
        assertEquals(expectedToDo, actual);
        assertEquals(actual.getTitle(), "Title-1");

        verify(toDoRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateToDo() {
        // Given
        ToDo updatedToDo = new ToDo();
        updatedToDo.setId(expectedToDo.getId());
        updatedToDo.setTitle("Title-updated");
        updatedToDo.setCreatedAt(LocalDateTime.now());
        updatedToDo.setOwner(user);

        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(updatedToDo));

        // When
        ToDo actual = toDoService.update(updatedToDo);

        // Assert
        assertEquals(updatedToDo, actual);
        assertEquals(actual.getTitle(), "Title-updated");

        verify(toDoRepository, times(1)).findById(anyLong());
        verify(toDoRepository, times(1)).save(updatedToDo);

    }

    @Test
    void testDeleteExistingTodo() {
        // Given
        long todoId = 1L;
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(expectedToDo));
        doNothing().when(toDoRepository).deleteById(todoId);

        // Then
        toDoService.delete(todoId);

        verify(toDoRepository, times(1)).findById(todoId);
        verify(toDoRepository, times(1)).deleteById(todoId);
    }

    @Test
    void testGetAllToDoByUserId() {
        // Given
        List<ToDo> expectedList = List.of(expectedToDo);
        when(toDoRepository.getAllToDoByUserId(anyLong())).thenReturn(List.of(expectedToDo));

        // When
        List<ToDo> actualList = toDoService.getByUserId(anyLong());

        // Assert
        assertEquals(expectedList, actualList);
        assertEquals(actualList.get(0).getTitle(), "Title-1");
    }

    @Test
    void testGetAllTodo() {
        // Given
        List<ToDo> expectedList = List.of(expectedToDo);

        when(toDoRepository.findAll()).thenReturn(expectedList);

        // When
        List<ToDo> actualList = toDoService.getAll();

        // Assert
        assertEquals(expectedList, actualList);
        assertEquals(actualList.get(0).getTitle(), "Title-1");

        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void testCheckIfToDoIsNull() {
        // When
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> toDoService.create(null)
        );
        // Assert
        assertEquals("Todo can not be a null", exception.getMessage());
    }

    @Test
    void testCheckIfToDoHasIncorrect() {
        // When
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> toDoService.create(null)
        );
        // Assert
        assertEquals("Todo can not be a null", exception.getMessage());
    }
}
