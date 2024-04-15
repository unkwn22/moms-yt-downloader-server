package com.example.momsytdownloaderserver.dto;

public class RegisterDto {

    String username;
    String password;
    String name;

    public RegisterDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public RegisterDto() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
