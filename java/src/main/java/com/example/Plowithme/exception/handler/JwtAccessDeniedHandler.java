package com.example.Plowithme.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;



public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        accessDeniedException = new AccessDeniedException("접근 권한이 없습니다.");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        //수정?
    }


}
