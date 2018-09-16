package com.test.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.userservice.model.UserRegistryModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerNewUser() throws Exception {

        UserRegistryModel userRegistryModel = new UserRegistryModel();
        userRegistryModel.setFirstName("UserName");
        userRegistryModel.setLastName("UserLastName");
        userRegistryModel.setUserName("userName");
        userRegistryModel.setPassword("1111");

        String userRegistryString = objectMapper.writeValueAsString(userRegistryModel);

        mvc.perform(post("/userservice/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(userRegistryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("id", not("")))
                .andExpect(jsonPath("firstName", is(userRegistryModel.getFirstName())))
                .andExpect(jsonPath("lastName", is(userRegistryModel.getLastName())))
                .andExpect(jsonPath("userName", is(userRegistryModel.getUserName())));
    }

    @Test
    public void registerSameUserTwoTimes() throws Exception {

        UserRegistryModel userRegistryModel = new UserRegistryModel();
        userRegistryModel.setFirstName("UserName2");
        userRegistryModel.setLastName("UserLastName2");
        userRegistryModel.setUserName("userName2");
        userRegistryModel.setPassword("1111");

        String userRegistryString = objectMapper.writeValueAsString(userRegistryModel);

        mvc.perform(post("/userservice/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(userRegistryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("id", not("")))
                .andExpect(jsonPath("firstName", is(userRegistryModel.getFirstName())))
                .andExpect(jsonPath("lastName", is(userRegistryModel.getLastName())))
                .andExpect(jsonPath("userName", is(userRegistryModel.getUserName())));

        mvc.perform(post("/userservice/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(userRegistryString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("code", is("USER_ALREADY_EXISTS")))
                .andExpect(jsonPath("description", is("A user with the given username already exists")));
    }
}