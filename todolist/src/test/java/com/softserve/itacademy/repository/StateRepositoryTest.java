package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class StateRepositoryTest {
    private final StateRepository stateRepository;

    @Autowired
    public StateRepositoryTest(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Test
    public void testGetByName(){
        State state1 = new State();
        state1.setName("state 1");
        stateRepository.save(state1);

        State state2 = new State();
        state2.setName("state 2");

        State expected = stateRepository.save(state2);
        State actual = stateRepository.getByName("state 2");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllStatesSorted(){
        State state1 = new State();
        state1.setName("state 1");
        stateRepository.save(state1);

        State state2 = new State();
        state2.setName("state 2");
        stateRepository.save(state2);

        State state3 = new State();
        state3.setName("My state");
        stateRepository.save(state3);

        State state4 = new State();
        state4.setName("New state");
        stateRepository.save(state4);

        List<State> expected = new ArrayList<>();
        expected.add(state3);
        expected.add(state4);
        expected.add(state1);
        expected.add(state2);

        List<State> actual = stateRepository.getAllStatesSorted();
        assertEquals(expected, actual);
    }
}
