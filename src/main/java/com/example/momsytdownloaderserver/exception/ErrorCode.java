package com.example.momsytdownloaderserver.exception;

public enum ErrorCode {

    TWOXXCODE("2xx"),
    FOURXXCODE("4xx"),
    FIVEXXCODE("5xx");

    private final String errorMessage;

    ErrorCode(String errorMessage) { this.errorMessage = errorMessage; }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return name() + " : " + errorMessage;
    }
}
