package com.example.momsytdownloaderserver.repository;

import com.example.momsytdownloaderserver.entity.User;
import com.example.momsytdownloaderserver.entity.UserInteraction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserInteractionImpl implements UserInteraction {

    private final UserRepository userRepository;

    public UserInteractionImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUserNameAndName(String username, String name) {
        return userRepository.findByUsernameAndName(username, name);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
