package com.example.momsytdownloaderserver.controller;

import com.example.momsytdownloaderserver.dto.LoginDto;
import com.example.momsytdownloaderserver.dto.RegisterDto;
import com.example.momsytdownloaderserver.exception.CommonResponse;
import com.example.momsytdownloaderserver.exception.CommonResult;
import com.example.momsytdownloaderserver.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final UserService userService;

    public ClientController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public CommonResult<String> userRegister(@RequestBody RegisterDto dto) {
        userService.registerUserLogic(dto);
        return CommonResponse.success("회원가입 성공");
    }

    @PostMapping("/login")
    public CommonResult<String> userLogin(@RequestBody LoginDto loginDto) {
        return CommonResponse.success(userService.loginUserLogic(loginDto));
    }
}