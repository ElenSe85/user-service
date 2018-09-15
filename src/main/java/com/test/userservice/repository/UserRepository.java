package com.test.userservice.repository;

import com.test.userservice.domain.User;
import com.test.userservice.exeption.UserAlreadyExistsException;

public interface UserRepository {

    /**
     * Create user
     *
     * @param user - User to create
     * @return User created user
     * @throws UserAlreadyExistsException - throws when given userName of User already exist
     * @see User
     */
    User createUser(User user) throws UserAlreadyExistsException;
}