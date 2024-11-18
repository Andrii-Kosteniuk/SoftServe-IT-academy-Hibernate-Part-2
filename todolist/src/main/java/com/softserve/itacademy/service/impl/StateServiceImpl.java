package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Override
    public State create(State state) {
        if(state != null){
            if(stateRepository.existsById(state.getId())){
                throw new EntityExistsException("State with id " + state.getId() + " already exists!");
            }
            return stateRepository.save(state);
        }
        else throw new IllegalArgumentException("State cannot be null!");
    }

    @Override
    public State readById(long id) {
        return stateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("State with id " + id + " not found!"));
    }

    @Override
    public State update(State state) {
        if(state != null){
            if(stateRepository.existsById(state.getId())){
                return stateRepository.save(state);
            }
            else throw new EntityExistsException("State with id " + state.getId() + " not found!");
        }
        else throw new IllegalArgumentException("State cannot be null!");
    }

    @Override
    public void delete(long id) {
        if(stateRepository.existsById(id)){
            stateRepository.deleteById(id);
        }
        else throw new EntityExistsException("State with id " + id + " not found!");
    }

    @Override
    public List<State> getAll() {
        return stateRepository.getAllStatesSorted();
    }

    @Override
    public State getByName(String name) {
        return stateRepository.getByName(name);
    }
}
