package com.example.momsytdownloaderserver.dto;

public class LoginDto {

    String username;
    String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
