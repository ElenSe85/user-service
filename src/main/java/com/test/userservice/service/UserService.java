package com.test.userservice.service;

import com.test.userservice.exeption.UserAlreadyExistsException;
import com.test.userservice.model.UserRegistryModel;
import com.test.userservice.model.UserResponseModel;

public interface UserService {

    /**
     * Register new user
     *
     * @param userRegistryModel - UserRegistryModel register user
     * @return UserResponseModel registered user
     * @throws UserAlreadyExistsException - throws when given userName of UserRegistryModel already exist
     * @see UserResponseModel
     */
    UserResponseModel registerUser(UserRegistryModel userRegistryModel) throws UserAlreadyExistsException;
}