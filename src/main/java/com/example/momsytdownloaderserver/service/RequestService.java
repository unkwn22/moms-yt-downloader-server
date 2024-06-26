package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.entity.Request;
import com.example.momsytdownloaderserver.entity.RequestEntityCommand;
import com.example.momsytdownloaderserver.entity.RequestInteraction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestService {

    private final RequestInteraction requestInteraction;

    public RequestService(RequestInteraction requestInteraction) {
        this.requestInteraction = requestInteraction;
    }

    @Transactional
    public RequestEntityCommand saveRequest(String originalTitle, String requestedTitle) {
        Request request = new Request(originalTitle, requestedTitle);
        Request savedRequest = requestInteraction.saveRequest(request);
        return constructRequestEntityCommand(savedRequest);
    }

    private RequestEntityCommand constructRequestEntityCommand(Request request) {
        return new RequestEntityCommand(
            request.getId(),
            request.getOriginalTitle(),
            request.getRequestedTitle()
        );
    }
}
