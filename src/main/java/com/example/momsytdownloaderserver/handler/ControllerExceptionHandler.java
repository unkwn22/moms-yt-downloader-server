package com.example.momsytdownloaderserver.handler;

import com.example.momsytdownloaderserver.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalErrorException.class)
    public CommonResult<String> handleInternalException(InternalErrorException e) {
        return CommonResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public CommonResult<String> handleUnauthorizedException(UnauthorizedException e) {
        return CommonResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public CommonResult<String> handleNotFoundException(NotFoundException e) {
        return CommonResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public CommonResult<String> handleBadRequestException(BadRequestException e) {
        return CommonResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public CommonResult<String> handleBaseException(BaseException e) {
        return CommonResponse.fail(e.getErrorCode(), e.getMessage());
    }
}
