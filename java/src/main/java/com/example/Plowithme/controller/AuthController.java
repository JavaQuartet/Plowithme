package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.user.LoginDto;
import com.example.Plowithme.dto.request.user.RegisterDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.dto.response.JWTAuthResponse;
import com.example.Plowithme.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "회원가입/로그인")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    @Operation(summary = "로그인")
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "로그인 성공", new JWTAuthResponse(token) );
        log.info("로그인 완료");
        return ResponseEntity.ok().body(response);
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