package com.example.momsytdownloaderserver.repository;

import com.example.momsytdownloaderserver.entity.Request;
import com.example.momsytdownloaderserver.entity.RequestInteraction;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RequestInteractionImpl implements RequestInteraction {

    private final RequestRepository requestRepository;

    public RequestInteractionImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request findRequestById(Long id) {
        Optional<Request> searchedRequest = requestRepository.findById(id.intValue());
        if(searchedRequest.isEmpty()) throw new NotFoundException(ErrorCode.INTERNAL);
        return searchedRequest.get();
    }

    @Override
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }
}
