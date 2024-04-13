package com.example.momsytdownloaderserver.controller;

import com.example.momsytdownloaderserver.dto.LoginDto;
import com.example.momsytdownloaderserver.dto.RegisterDto;
import com.example.momsytdownloaderserver.exception.CommonResponse;
import com.example.momsytdownloaderserver.exception.CommonResult;
import com.example.momsytdownloaderserver.repository.YouTubeSearchResponse;
import com.example.momsytdownloaderserver.service.SearchService;
import com.example.momsytdownloaderserver.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final UserService userService;
    private final SearchService searchService;

    public ClientController(UserService userService, SearchService searchService) {
        this.userService = userService;
        this.searchService = searchService;
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

    @GetMapping("/search")
    public CommonResult<YouTubeSearchResponse> youtubeSearch(
        Authentication authentication,
        @RequestParam(required = false) String query,
        @RequestParam(required = false) String pageToken
    ) {
        return CommonResponse.success(searchService.search(query, pageToken), "조회 성공");
    }
}
