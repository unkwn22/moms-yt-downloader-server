package com.example.momsytdownloaderserver.entity;

public interface UserInteraction {

    User saveUser(User user);

    User findUserByUserNameAndName(String userName, String name);
}
