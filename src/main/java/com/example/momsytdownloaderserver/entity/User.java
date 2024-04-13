package com.example.momsytdownloaderserver.entity;

import com.example.momsytdownloaderserver.util.DateUtil;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    public User(
        String name,
        String username,
        String password
    ) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, updatable = true)
    protected String name;

    @Column(name = "password", nullable = false, updatable = true)
    protected String password;

    @Column(name = "username", nullable = false, updatable = true)
    protected String username;

    @Column(name = "authorize_status", nullable = false, updatable = true)
    protected int authorizeStatus = 0;

    @Column(name = "created_dt", nullable = false, updatable = false)
    protected LocalDateTime createdDt = DateUtil.getCurrentDateTime();

    @Column(name = "updated_dt", nullable = false, updatable = true)
    protected LocalDateTime updatedDt = DateUtil.getCurrentDateTime();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAuthorizeStatus() {
        return authorizeStatus;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public LocalDateTime getUpdatedDt() {
        return updatedDt;
    }
}
