package com.example.momsytdownloaderserver.exception;

import com.example.momsytdownloaderserver.util.DateUtil;

public class CommonResult<T> {

    private String timestamp = DateUtil.getCurrentDateTime().toString();
    private Result result;
    private String message;
    private T data;
    private ErrorCode errorCode;

    public CommonResult(Result result, T data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }

    public CommonResult(Result result, String message) {
        this.result = result;
        this.message = message;
    }

    public CommonResult(Result result, String errorMessage, ErrorCode errorCode) {
        this.result = result;
        this.message = errorMessage;
        this.errorCode = errorCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Result getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
