package com.test.userservice.service;

import com.test.userservice.domain.User;
import com.test.userservice.exeption.UserAlreadyExistsException;
import com.test.userservice.model.UserRegistryModel;
import com.test.userservice.model.UserResponseModel;
import com.test.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseModel registerUser(UserRegistryModel userRegistryModel) throws UserAlreadyExistsException {

        User newUser = new User(userRegistryModel.getFirstName(), userRegistryModel.getLastName(),
                userRegistryModel.getUserName(), userRegistryModel.getPassword());

        User registeredUser = userRepository.createUser(newUser);
        LOGGER.info("User [" + registeredUser.getUserName() + "] successfully registered.");

        return UserResponseModel.mapUserToUserResponseModel(registeredUser);
    }
}