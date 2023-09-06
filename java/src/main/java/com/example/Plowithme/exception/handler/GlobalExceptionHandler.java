package com.example.Plowithme.exception.handler;

import com.example.Plowithme.exception.custom.*;
import com.example.Plowithme.exception.error.BaseException;
import com.example.Plowithme.exception.error.ErrorCode;
import com.example.Plowithme.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.HashMap;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
    Common
     */

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        log.error("=====error occurred===== BaseException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    //존재하지 않는 리소스 호출
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception e){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                e.getMessage());
        log.error("=====error occurred===== UsernameNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    /**
     글로벌 에러
     */

    //서버 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e){
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage());
        log.error("=====error occurred===== Exception : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //잘못된 인자
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e){
        ErrorResponse response = new ErrorResponse((HttpStatus.BAD_REQUEST.value()), e.getMessage());
        log.error("=====error occurred===== IllegalArgumentException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    //존재하지 않는 http 메소드 호출
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        ErrorResponse response = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
         log.error("=====error occurred===== HttpRequestMethodNotSupportedException : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);

    }


    //@Vaild 검증
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HashMap<String, Object> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse response = new ErrorResponse(ErrorCode.METHOD_ARGUMENT_NOT_VALID, errors);
        log.error("=====error occurred===== MethodArgumentNotValidException:" + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    //접근 권한
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, WebRequest webRequest){
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                webRequest.getDescription(false));
        log.error("=====error occurred===== AccessDeniedException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    /**
    로그인, 회원가입
     */

    //이메일 중복 검증
    @ExceptionHandler(UserEmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailAlreadyExistException(UserEmailAlreadyExistException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.EMAIL_DUPLICATE);
        log.error("=====error occurred===== UserEmailAlreadyExistException : " + e.getMessage());
        return ResponseEntity.status(ErrorCode.EMAIL_DUPLICATE.getStatus()).body(response);

    }


    /**
     이미지
     */

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorResponse> handleFileException(FileException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.error("=====error occurred===== UserFileException : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }


    /**
     모임
     */

    @ExceptionHandler(ClassException.class)
    public ResponseEntity<ErrorResponse> OutOfMaxParticipantException(ClassException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.error("=====error occurred===== UserFileException : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }


}
