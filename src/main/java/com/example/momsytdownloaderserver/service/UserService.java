package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.config.authentication.JwtTokenGenerator;
import com.example.momsytdownloaderserver.dto.LoginDto;
import com.example.momsytdownloaderserver.dto.RegisterDto;
import com.example.momsytdownloaderserver.entity.User;
import com.example.momsytdownloaderserver.entity.UserInteraction;
import com.example.momsytdownloaderserver.exception.BadRequestException;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.NotFoundException;
import com.example.momsytdownloaderserver.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserInteraction userInteraction;
    private final JwtTokenGenerator jwtTokenGenerator;

    public UserService(UserInteraction userInteraction, JwtTokenGenerator jwtTokenGenerator) {
        this.userInteraction = userInteraction;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Transactional
    public void registerUserLogic(RegisterDto dto) {
        Optional<User> searchedUser = userInteraction.findByUserName(dto.getUsername());
        if(searchedUser.isPresent()) throw new BadRequestException(ErrorCode.EXISTS);
        User beforeRegisterUserEntity = new User(dto.getName(), dto.getUsername(), dto.getPassword());
        userInteraction.saveUser(beforeRegisterUserEntity);
    }

    @Transactional(readOnly = true)
    public String loginUserLogic(LoginDto dto) {
        Optional<User> searchUser = userInteraction.findByUserName(dto.getUsername());
        if(searchUser.isEmpty()) throw new NotFoundException(ErrorCode.CANNOT_FIND);
        if(!dto.getPassword().equals(searchUser.get().getPassword())) {
            throw new NotFoundException(ErrorCode.CANNOT_FIND);
        }
        User foundUser = searchUser.get();
        if(foundUser.getAuthorizeStatus() == 0) throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        return jwtTokenGenerator.createToken(searchUser.get());
    }
}
