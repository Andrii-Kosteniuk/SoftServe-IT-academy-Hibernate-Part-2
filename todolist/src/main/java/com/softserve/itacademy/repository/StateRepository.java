package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

// Implement the two methods:

    // Method for retrieving a State by name
    // Method for retrieving all State objects sorted by name

    @Query(value = "SELECT id, name FROM States WHERE name = :stateName", nativeQuery=true)
    State getByName(@Param("stateName") String stateName);

    @Query(value = "SELECT * FROM States ORDER BY name", nativeQuery=true)
    List<State> getAllStatesSorted();
}
