package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on User entities.
 * Extends JpaRepository for basic CRUD functionality.
 * <p>
 * Includes a custom query to fetch User object based on the email address.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
// Implement a method that retrieves a User object based on the email address.
// The method should accept a String value representing the user's email as a parameter.
// It should return an Optional<User>, where the result is either the found User object or empty if no user is found.

    /**
     * Retrieves a User object based on the email address.
     *
     * @param "email" user's email
     * @return Optional<User>, where the result is either the found User object or empty if no user is found
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

}
