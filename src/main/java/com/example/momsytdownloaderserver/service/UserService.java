package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.entity.UserInteraction;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserInteraction userInteraction;

    public UserService(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }
}
