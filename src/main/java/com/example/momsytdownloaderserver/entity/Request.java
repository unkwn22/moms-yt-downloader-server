package com.example.momsytdownloaderserver.entity;

import com.example.momsytdownloaderserver.util.DateUtil;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "request")
public class Request {

    public Request(
        String originalTitle,
        String requestedTitle
    ) {
        this.originalTitle = originalTitle;
        this.requestedTitle = requestedTitle;
    }

    public Request() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "original_title", nullable = false, updatable = false, columnDefinition = "MEDIUMTEXT")
    protected String originalTitle;

    @Column(name = "requested_title", nullable = true, updatable = false, columnDefinition = "MEDIUMTEXT")
    protected String requestedTitle;

    @Column(name = "created_dt", nullable = false, updatable = false)
    protected LocalDateTime createdDt = DateUtil.getCurrentDateTime();

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getRequestedTitle() {
        return requestedTitle;
    }

    public void setRequestedTitle(String requestedTitle) {
        this.requestedTitle = requestedTitle;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

    public Long getId() {
        return id;
    }
}
