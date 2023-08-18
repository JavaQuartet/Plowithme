package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.LoginDto;
import com.example.Plowithme.dto.request.RegisterDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "회원가입/로그인")
public class AuthController {

    private final AuthService authService;
    @GetMapping("/hello")
    public ResponseEntity<?> Hello(){
        return ResponseEntity.ok("hello");
    }

//    @PostMapping(value = "/login")
//    @Operation(summary = "로그인")
//    public ResponseEntity<String> login(@Validated @RequestBody LoginDto loginDto) {
//
//
//        String token = authService.login(loginDto);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization", token);
//        return new ResponseEntity(httpHeaders, HttpStatus.OK);
//
//    }

    @PostMapping(value = "/login")
    @Operation(summary = "로그인")
    public ResponseEntity<CommonResponse> login(@Validated @RequestBody LoginDto loginDto) {

        String token = authService.login(loginDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "로그인 성공");
        log.info("로그인 완료");
        return ResponseEntity.ok().header("Authorization").body(response);
    }



    @PostMapping(value = "/sign-up")
    @Operation(summary = "회원 가입")
    public ResponseEntity<CommonResponse> register (@Valid @RequestBody RegisterDto registerDto) {
        authService.register(registerDto);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(),"회원가입 성공");
        log.info("회원가입 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}