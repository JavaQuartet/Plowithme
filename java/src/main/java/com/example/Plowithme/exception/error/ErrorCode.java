package com.example.Plowithme.exception.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Common

    METHOD_ARGUMENT_NOT_VALID(400, HttpStatus.BAD_REQUEST, "유효하지 않은 인자 입력"),

    //로그인, 회원가입, 인증
    EMAIL_DUPLICATE(409,HttpStatus.CONFLICT, "이미 등록된 이메일입니다."),


    UNKNOWN_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "확인된지 않은 에러"),
    PERMISSION_DENIED(401, HttpStatus.UNAUTHORIZED,"접근 거부");

    //마이페이지

    private final int code;
    private final HttpStatus status;
    private final String message;
}
