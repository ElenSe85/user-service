package com.test.userservice.model;

import com.test.userservice.domain.User;

public class UserResponseModel {

    private String id;
    private String firstName;
    private String lastName;
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static UserResponseModel mapUserToUserResponseModel(User user){
        UserResponseModel userResponseModel = new UserResponseModel();
        userResponseModel.setId(user.getId());
        userResponseModel.setFirstName(user.getFirstName());
        userResponseModel.setLastName(user.getLastName());
        userResponseModel.setUserName(user.getUserName());
        return userResponseModel;
    }
}