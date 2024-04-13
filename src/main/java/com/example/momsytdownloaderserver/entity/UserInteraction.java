package com.example.momsytdownloaderserver.entity;

import java.util.Optional;

public interface UserInteraction {

    User saveUser(User user);

    Optional<User> findUserByUserNameAndName (String userName, String name);
}
