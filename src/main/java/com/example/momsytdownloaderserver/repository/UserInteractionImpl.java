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
        return userRepository.saveUser(user);
    }

    @Override
    public User findUserByUserNameAndName(String userName, String name) {
        Optional<User> optionalUser = userRepository.findByUsernameAndName(userName, name);
        if(optionalUser.isEmpty()) throw new RuntimeException("User not found");
        return optionalUser.get();
    }
}
