package com.test.userservice.controller;

import com.test.userservice.exeption.UserAlreadyExistsException;
import com.test.userservice.model.UserRegistryModel;
import com.test.userservice.model.UserResponseModel;
import com.test.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userservice")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseModel registerUser(@Valid @RequestBody UserRegistryModel userRegistryModel)
            throws UserAlreadyExistsException {

        return userService.registerUser(userRegistryModel);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException() {

        Map<String, String> response = new HashMap<>();
        response.put("code", "USER_ALREADY_EXISTS");
        response.put("description", "A user with the given username already exists");

        return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
    }
}