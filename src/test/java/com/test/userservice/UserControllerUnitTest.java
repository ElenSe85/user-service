package com.test.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.userservice.controller.UserController;
import com.test.userservice.exeption.UserAlreadyExistsException;
import com.test.userservice.model.UserRegistryModel;
import com.test.userservice.model.UserResponseModel;
import com.test.userservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerNewUser() throws Exception {

        UserRegistryModel userRegistryModel = new UserRegistryModel();
        userRegistryModel.setFirstName("UserName");
        userRegistryModel.setLastName("UserLastName");
        userRegistryModel.setUserName("userName");
        userRegistryModel.setPassword("1111");

        UserResponseModel userResponseModel = new UserResponseModel();
        userResponseModel.setId("1");
        userResponseModel.setLastName("UserLastName");
        userResponseModel.setFirstName("UserName");
        userResponseModel.setUserName("userName");

        String userRegistryString = objectMapper.writeValueAsString(userRegistryModel);
        String userResponseString = objectMapper.writeValueAsString(userResponseModel);

        when(userService.registerUser(ArgumentMatchers.any(UserRegistryModel.class))).thenReturn(userResponseModel);

        mvc.perform(post("/userservice/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(userRegistryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userResponseString))
                .andReturn();
    }

    @Test
    public void registerUserThatAlreadyExist() throws Exception {

        UserRegistryModel userRegistryModel = new UserRegistryModel();
        userRegistryModel.setFirstName("UserName");
        userRegistryModel.setLastName("UserLastName");
        userRegistryModel.setUserName("userName");
        userRegistryModel.setPassword("1111");

        String userRegistryString = objectMapper.writeValueAsString(userRegistryModel);

        when(userService.registerUser(ArgumentMatchers.any(UserRegistryModel.class)))
                .thenThrow(new UserAlreadyExistsException("A user with the given username already exists"));

        mvc.perform(post("/userservice/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(userRegistryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("code", is("USER_ALREADY_EXISTS")))
                .andExpect(jsonPath("description", is("A user with the given username already exists")));
    }

    @Test
    public void registerNotValidUserName() throws Exception {

        UserRegistryModel userRegistryModel = new UserRegistryModel();
        userRegistryModel.setFirstName("UserName");
        userRegistryModel.setLastName("UserLastName");
        userRegistryModel.setPassword("1111");

        String userRegistryString = objectMapper.writeValueAsString(userRegistryModel);

        mvc.perform(post("/userservice/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(userRegistryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}