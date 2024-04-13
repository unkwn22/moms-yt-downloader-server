package com.example.momsytdownloaderserver.entity;

import java.util.Objects;

public record RequestEntityCommand(
    Long id,
    String originalTitle,
    String requestedTitle
) {

    public RequestEntityCommand {
        Objects.requireNonNull(id);
        Objects.requireNonNull(originalTitle);
    }
}
