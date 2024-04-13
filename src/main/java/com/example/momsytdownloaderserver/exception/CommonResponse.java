package com.example.momsytdownloaderserver.exception;

public class CommonResponse {

    public static <T> CommonResult<T> success (T data, String message) {
        return new CommonResult<>(Result.SUCCESS, data, message);
    }

    public static CommonResult<String> success (String message) {
        return new CommonResult<>(Result.SUCCESS, message);
    }

    public static CommonResult<String> fail (ErrorCode errorCode, String message) {
        return new CommonResult<>(Result.FAIL, message, errorCode);
    }

    public static CommonResult<String> fail (ErrorCode errorCode) {
        return new CommonResult<>(Result.FAIL, errorCode.getErrorMessage(), errorCode);
    }
}