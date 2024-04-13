package com.example.momsytdownloaderserver.exception;

public class InternalErrorException extends RuntimeException {

    private final ErrorCode errorCode;

    public InternalErrorException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
