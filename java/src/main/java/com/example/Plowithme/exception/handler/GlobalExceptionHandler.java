package com.example.Plowithme.exception.handler;

import com.example.Plowithme.exception.custom.FileException;
import com.example.Plowithme.exception.custom.TokenException;
import com.example.Plowithme.exception.custom.UserEmailAlreadyExistException;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.exception.error.BaseException;
import com.example.Plowithme.exception.error.ErrorCode;
import com.example.Plowithme.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

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

//    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
//    public ResponseEntity<ErrorResponse> handleIOException(Exception e){
//        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
//        log.error("=====error occurred===== Unauthorized : " + e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }


    //존재하지 않는 리소스 호출
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception e){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                e.getMessage());
        log.error("=====error occurred===== UsernameNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //잘못된 인자
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e){
        ErrorResponse response = new ErrorResponse((HttpStatus.BAD_REQUEST.value()), e.getMessage());
        log.error("=====error occurred===== IllegalArgumentException : " + e.getMessage());
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
    //존재하지 않는 http 메소드 호출
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        ErrorResponse response = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
         log.error("=====error occurred===== HttpRequestMethodNotSupportedException : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);

    }
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
//

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

    //토큰
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        log.error("=====error occurred===== UserTokenException : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

    }



    //파일
    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorResponse> handleFileException(FileException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.error("=====error occurred===== UserFileException : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }


//    @ExceptionHandler(SignatureException.class)
//    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException e) {
//        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_TOKEN);
//        log.error("=====error occurred===== SignatureException : " + ErrorCode.INVALID_TOKEN.getMessage());
//        return ResponseEntity.status(ErrorCode.INVALID_TOKEN.getStatus()).body(response);
//    }
//
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException e) {
//        ErrorResponse response = new ErrorResponse(ErrorCode.WRONG_TOKEN);
//        log.error("=====error occurred===== MalformedJwtException : " + ErrorCode.WRONG_TOKEN.getMessage());
//        return ResponseEntity.status(ErrorCode.WRONG_TOKEN.getStatus()).body(response);
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e) {
//        ErrorResponse response = new ErrorResponse(ErrorCode.TOKEN_EXPIRED);
//        log.error("=====error occurred===== ExpiredJwtException : " + ErrorCode.TOKEN_EXPIRED.getMessage());
//        return ResponseEntity.status(ErrorCode.TOKEN_EXPIRED.getStatus()).body(response);
//    }
//
//    @ExceptionHandler(UnsupportedJwtException.class)
//    public ResponseEntity<ErrorResponse> handleUnsupportedJwtException(UnsupportedJwtException e){
//        ErrorResponse response = new ErrorResponse(ErrorCode.UNSUPPORTED_TOKEN);
//        log.error("=====error occurred===== UnsupportedJwtException : " + ErrorCode.UNSUPPORTED_TOKEN.getMessage());
//        return ResponseEntity.status(ErrorCode.UNSUPPORTED_TOKEN.getStatus()).body(response);
//    }

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
