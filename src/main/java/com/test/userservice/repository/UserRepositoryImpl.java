package com.test.userservice.repository;

import com.test.userservice.domain.User;
import com.test.userservice.exeption.UserAlreadyExistsException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Imitation of User Repository implementation
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private List<User> users = new ArrayList<>();
    private Integer userId = 1;

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {

        isUserNameExist(user.getUserName());
        user.setId(userId.toString());
        //hash password omitted
        users.add(user);
        userId++;
        return user;
    }

    /**
     * Check is userName already exist
     *
     * @param userName - String userName for check
     * @throws UserAlreadyExistsException - throws when given userName already exist
     */
    private void isUserNameExist(String userName) throws UserAlreadyExistsException {

        boolean isExist = users.stream().anyMatch(u -> u.getUserName().equals(userName));
        if (isExist) {
            throw new UserAlreadyExistsException("A user with the given username already exists");
        }
    }
}