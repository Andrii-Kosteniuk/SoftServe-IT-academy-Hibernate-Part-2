package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.impl.StateServiceImpl;
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

    @AfterEach
    void clear() {
        expected = null;
    }

    @Test
   void testCorrectCreate(){
        when(stateRepository.save(expected)).thenReturn(expected);
        State actual = stateService.create(expected);

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).save(expected);
    }

    @Test
    void testExceptionCreateNull(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> stateService.create(null)
        );

        assertEquals("State cannot be null!", exception.getMessage());
        verify(stateRepository, never()).save(new State());
    }

    @Test
    void testCorrectReadById(){
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
    void testUpdate() {
        when(stateRepository.existsById(expected.getId())).thenReturn(true);
        when(stateRepository.save(expected)).thenReturn(expected);
        State state = stateService.update(expected);
        assertEquals(expected, state);
        verify(stateRepository, times(1)).existsById(expected.getId());
        verify(stateRepository, times(1)).save(expected);
    }

    @Test
    void testExceptionUpdateNull(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> stateService.update(null)
        );

        assertEquals("State cannot be null!", exception.getMessage());
        verify(stateRepository, never()).save(new State());
    }

    @Test
     void testDelete(){
        when(stateRepository.existsById(expected.getId())).thenReturn(true);
        doNothing().when(stateRepository).deleteById(expected.getId());
        stateService.delete(expected.getId());
        verify(stateRepository, times(1)).existsById(expected.getId());
        verify(stateRepository, times(1)).deleteById(expected.getId());
    }


    @Test
    void testGetByName() {
        when(stateRepository.getByName("My state")).thenReturn(expected);
        State actual = stateService.getByName("My state");
        assertEquals(expected, actual);
        verify(stateRepository, times(1)).getByName("My state");
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
