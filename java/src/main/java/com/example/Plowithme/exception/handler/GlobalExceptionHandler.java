package com.example.Plowithme.exception.handler;

import com.example.Plowithme.exception.custom.UserEmailAlreadyExistException;
import com.example.Plowithme.exception.error.BaseException;
import com.example.Plowithme.exception.error.ErrorCode;
import com.example.Plowithme.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    Common
     */

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        log.error("=====error occurred===== BaseException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /*
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(Exception e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        log.error("=====error occurred===== UsernameNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatusCode status,
//                                                                  WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) ->{
//            String fieldName = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//        });
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
////
//    //존재하지 않는 http 메소드 호출
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    private ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//
//        ErrorResponse response = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
//         log.error("=====error occurred===== HttpRequestMethodNotSupportedException : " + e.getMessage());
//        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
//
//    }
//


//    @Override
//    protected Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        });
//        return errorMap;
//    }
//
//    @Vaild 검증(어노테이션 제한)
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errors.put(error.getField(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//        });
//
//        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors.toString());
//        log.error("=====error occurred===== MethodArgumentNotValid : " + ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
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


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Object processValidationError(MethodArgumentNotValidException ex) {
////        Map<String, String> errorMap = new HashMap<>();
////        ex.getBindingResult().getFieldErrors().forEach(error -> {
////            errorMap.put(error.getField(), error.getDefaultMessage());
////        });
//        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE_BINDING.getCode(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//        return response;
//    }



    //접근 권한
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, WebRequest webRequest){
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                webRequest.getDescription(false));
        log.error("=====error occurred===== AccessDeniedException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /*
    로그인, 회원가입
     */

    //이메일 중복 검증
    @ExceptionHandler(UserEmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailAlreadyExistException(UserEmailAlreadyExistException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.EMAIL_DUPLICATE);
        log.error("=====error occurred===== UserEmailAlreadyExistException : " + e.getMessage());
        return ResponseEntity.status(ErrorCode.EMAIL_DUPLICATE.getStatus()).body(response);

    }


    /*
    마이페이지
     */

      /*
    커뮤니티
     */

    /*
    모임
     */


//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(UserNotFoundException.class)
//    public Map<String, String> handleBusinessException(UserNotFoundException ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put("errorMessage", ex.getMessage());
//        return errorMap;
//    }

}
