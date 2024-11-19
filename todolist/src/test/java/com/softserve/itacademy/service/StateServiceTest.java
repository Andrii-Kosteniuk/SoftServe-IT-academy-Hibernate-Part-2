package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.impl.StateServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StateServiceTest {
    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    private State expected;

    @BeforeEach
    void setUp() {
        expected = new State();
        expected.setName("My state");
    }

//    @AfterEach
//    public void tearDown() {
//        expected = null;
//    }

    @Test
    public void testCorrectCreate(){
        when(stateRepository.save(expected)).thenReturn(expected);
        State actual = stateService.create(expected);

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionCreateNull(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> stateService.create(null)
        );

        assertEquals("State cannot be null!", exception.getMessage());
        verify(stateRepository, never()).save(new State());
    }

    @Test
    public void testCorrectReadById(){
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        State actual = stateService.readById(anyLong());

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).findById(anyLong());
    }

    @Test
    void testExceptionReadById() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> stateService.readById(-1L)
        );

        assertEquals("State with id -1 not found!", exception.getMessage());
        verify(stateRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAll() {
        List<State> expectedStates = List.of(new State(), new State(), new State());
        when(stateRepository.findAll()).thenReturn(expectedStates);
        List<State> actual = stateService.getAll();

        assertEquals(expectedStates, actual);
        verify(stateRepository, times(1)).findAll();
    }
}
