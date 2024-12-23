package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testGetByEmail_1() {
        User user1 = new User();
        user1.setFirstName("Mike");
        user1.setLastName("Green");
        user1.setEmail("mike@mail.com");
        user1.setPassword("Qwerty1!");
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Nick");
        user2.setLastName("Brown");
        user2.setEmail("nick@mail.com");
        user2.setPassword("Qwerty2!");

        User expected = userRepository.save(user2);
        User actual = userRepository.findByEmail("nick@mail.com").orElseThrow();

        assertEquals(expected, actual);
    }

    @Test
    void testGetByEmail_2() {
        User user = new User();
        user.setFirstName("Mike");
        user.setLastName("Green");
        user.setEmail("mike@mail.com");
        user.setPassword("Qwerty3!");
        userRepository.save(user);

        Optional<User> actual = userRepository.findByEmail("nick@mail.com");
        assertThat(actual).isEmpty();
    }

}

