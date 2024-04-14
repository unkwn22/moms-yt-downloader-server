package com.example.momsytdownloaderserver.entity;

public interface RequestInteraction {

    Request findRequestById(Long id);

    Request saveRequest(Request request);
}
