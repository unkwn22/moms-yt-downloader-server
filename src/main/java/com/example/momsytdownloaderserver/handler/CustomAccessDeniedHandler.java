package com.example.momsytdownloaderserver.handler;

import com.example.momsytdownloaderserver.exception.CommonResponse;
import com.example.momsytdownloaderserver.exception.CommonResult;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        CommonResult<String> exceptionResponse = CommonResponse.fail(ErrorCode.UNAUTHORIZED, ErrorCode.UNAUTHORIZED.getErrorMessage());
        response.getWriter().write(new Gson().toJson(exceptionResponse));
    }
}
