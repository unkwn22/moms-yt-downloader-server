package com.example.momsytdownloaderserver.exception;

public enum ErrorCode {

    UNAUTHORIZED("허용되지 않는 회원입니다."),
    JWT_EXPIRED("토큰 기간이 지났습니다."),
    JWT_CORRUPT("변조된 토큰입니다."),
    CANNOT_FIND("회원을 찾지 못했습니다."),
    NOT_EXISTS("존재하지 않는 회원입니다."),
    EXISTS("이미 존재하는 회원입니다."),
    INTERNAL("서버에 문제가 생겼습니다. 잠시 후 다시 이용해주세요.");

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
