package com.example.momsytdownloaderserver.entity;

import java.util.Optional;

public interface UserInteraction {

    User saveUser(User user);

    Optional<User> findUserByUserNameAndName (String username, String name);

    Optional<User> findByUserName(String username);
}
