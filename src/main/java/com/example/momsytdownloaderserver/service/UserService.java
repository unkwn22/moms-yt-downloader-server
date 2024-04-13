package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.dto.LoginDto;
import com.example.momsytdownloaderserver.dto.RegisterDto;
import com.example.momsytdownloaderserver.entity.User;
import com.example.momsytdownloaderserver.entity.UserInteraction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserInteraction userInteraction;

    public UserService(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }

    public void registerUserLogic(RegisterDto dto) {
        Optional<User> searchedUser = userInteraction.findUserByUserNameAndName(dto.getUsername(), dto.getName());
        // TODO custom response
        if(searchedUser.isPresent()) throw new RuntimeException("존재하는 회원입니다.");
        User beforeRegisterUserEntity = new User(dto.getName(), dto.getUsername(), dto.getPassword());
        userInteraction.saveUser(beforeRegisterUserEntity);
    }

    // TODO jwt return type
    public void loginUserLogic(LoginDto dto) {
        Optional<User> searchUser = userInteraction.findByUserName(dto.getUsername());
        // TODO custom response
        if(searchUser.isEmpty()) throw new RuntimeException("회원을 찾지 못했습니다.");
        if(!dto.getPassword().equals(searchUser.get().getPassword())) {
            throw new RuntimeException("회원을 찾지 못했습니다.");
        }
    }
}
