package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.UserAlreadyExistsException;
import com.softserve.itacademy.exception.UserNotFoundException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;

import com.softserve.itacademy.utilty.DataVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public User create(User user) {
        DataVerification.checkUserIsValidOrNot(user);

        if (userRepository.existsById(user.getId())) {
            String errorMessage = "User with id " + user.getId() + " already exists in the database.";
            logger.info(errorMessage);
            throw new UserAlreadyExistsException(errorMessage);
        }
        User userToSave = userRepository.save(user);
        logger.info("User " + userToSave.getFirstName() + " was saved to the database");
        return userToSave;
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User update(User user) {
        DataVerification.checkUserIsValidOrNot(user);

        User updatedUser = readById(user.getId());

        userRepository.save(updatedUser);
        logger.log(Level.INFO, "User " + updatedUser.getFirstName() + " was updated successfully");
        return updatedUser;
    }

    @Override
    public void delete(long id) {
        readById(id);
        userRepository.deleteById(id);
        logger.log(Level.INFO, "User with ID " + id + " was successfully deleted from the database");
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

}
