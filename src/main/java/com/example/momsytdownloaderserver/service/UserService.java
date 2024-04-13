package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.config.authentication.JwtTokenGenerator;
import com.example.momsytdownloaderserver.dto.LoginDto;
import com.example.momsytdownloaderserver.dto.RegisterDto;
import com.example.momsytdownloaderserver.entity.User;
import com.example.momsytdownloaderserver.entity.UserInteraction;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserInteraction userInteraction;
    private final JwtTokenGenerator jwtTokenGenerator;

    public UserService(UserInteraction userInteraction, JwtTokenGenerator jwtTokenGenerator) {
        this.userInteraction = userInteraction;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public void registerUserLogic(RegisterDto dto) {
        Optional<User> searchedUser = userInteraction.findUserByUserNameAndName(dto.getUsername(), dto.getName());
        if(searchedUser.isPresent()) throw new UnauthorizedException(ErrorCode.FOURXXCODE, "존재하는 회원입니다.");
        User beforeRegisterUserEntity = new User(dto.getName(), dto.getUsername(), dto.getPassword());
        userInteraction.saveUser(beforeRegisterUserEntity);
    }

    public String loginUserLogic(LoginDto dto) {
        Optional<User> searchUser = userInteraction.findByUserName(dto.getUsername());
        if(searchUser.isEmpty()) throw new UnauthorizedException(ErrorCode.FOURXXCODE, "회원을 찾지 못했습니다.");
        if(!dto.getPassword().equals(searchUser.get().getPassword())) {
            throw new UnauthorizedException(ErrorCode.FOURXXCODE, "회원을 찾지 못했습니다.");
        }
        return jwtTokenGenerator.createToken(searchUser.get());
    }
}
