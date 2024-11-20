package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

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
class ToDoRepositoryTest {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    private User owner;
    private User collaborator;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setEmail("john.doe@example.com");
        owner.setPassword("password123");
        owner.setRole(UserRole.USER);
        userRepository.save(owner);

        collaborator = new User();
        collaborator.setFirstName("Jane");
        collaborator.setLastName("Smith");
        collaborator.setEmail("jane.smith@example.com");
        collaborator.setPassword("password123");
        collaborator.setRole(UserRole.USER);
        userRepository.save(collaborator);

        ToDo todo1 = new ToDo();
        todo1.setTitle("Title-owner");
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setOwner(owner);
        toDoRepository.save(todo1);

        ToDo todo2 = new ToDo();
        todo2.setTitle("Title-collaborator");
        todo2.setCreatedAt(LocalDateTime.now());
        todo2.setOwner(owner);
        todo2.setCollaborators(List.of(collaborator));
        toDoRepository.save(todo2);
    }

    @Test
    void testGetAllToDoByUserId_AsOwner() {
        List<ToDo> todos = toDoRepository.getAllToDoByUserId(owner.getId());

        assertThat(todos).hasSize(2);
        assertThat(todos).extracting(ToDo::getTitle).contains("Title-owner", "Title-collaborator");
    }

    @Test
    void testGetAllToDoByUserId_AsCollaborator() {
        List<ToDo> todos = toDoRepository.getAllToDoByUserId(collaborator.getId());

        assertThat(todos).hasSize(1);
        assertThat(todos.get(0).getTitle()).isEqualTo("Title-collaborator");
    }

    @Test
    void testGetAllToDoByUserId_NoMatch() {
        List<ToDo> todos = toDoRepository.getAllToDoByUserId(999L);

        assertThat(todos).isEmpty();
    }
}
