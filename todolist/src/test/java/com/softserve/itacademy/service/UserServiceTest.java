package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.UserNotFoundException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.model.UserRole;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User expected;

    @BeforeEach
    public void setUp() {
        expected = new User();
        expected.setFirstName("Mike");
        expected.setLastName("Green");
        expected.setEmail("green@mail.com");
        expected.setPassword("Qwerty1!");
        expected.setRole(UserRole.USER);
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void testCorrectCreate() {
        when(userRepository.save(expected)).thenReturn(expected);
        User actual = userService.create(expected);

        assertEquals(expected, actual);
        verify(userRepository, times(1)).save(expected);
    }

    @Test
    void testExceptionCreate() {
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> userService.create(null)
        );

        assertEquals("User cannot be 'null'", exception.getMessage());
        verify(userRepository, never()).save(new User());
    }

    @Test
    void testCorrectReadById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        User actual = userService.readById(anyLong());

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void testExceptionReadById() {
        Exception exception = assertThrows(UserNotFoundException.class, ()
                -> userService.readById(- 1L)
        );

        assertEquals("User with id -1 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        long userId = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(userRepository).deleteById(userId);

        userService.delete(userId);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetAll() {
        List<User> expectedUsers = List.of(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actual = userService.getAll();

        assertEquals(expectedUsers, actual);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testExceptionLoadUserByUsername() {
        assertThat(userService.findByEmail(anyString())).isEmpty();
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}
