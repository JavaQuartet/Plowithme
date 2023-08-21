package com.example.Plowithme.exception.handler;

import com.example.Plowithme.exception.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;



public class JwtAccessDeniedHandler implements AccessDeniedHandler {
//
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        accessDeniedException = new AccessDeniedException("접근 권한이 없습니다.");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
    }

//    //

//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
//        ErrorCode errorCode;
//        errorCode = ErrorCode.PERMISSION_DENIED;
//        setResponse(response, errorCode);
//
//    }
//
//    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("message", errorCode.getMessage());
//        responseJson.put("code", errorCode.getCode());
//
//        response.getWriter().print(responseJson);
//    }

}
