package com.example.Plowithme.service;

import com.example.Plowithme.dto.user.LoginDto;
import com.example.Plowithme.dto.user.RegisterDto;
import com.example.Plowithme.entity.Role;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.UserEmailAlreadyExistException;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    //회원가입
    @Transactional
    public void register(RegisterDto registerDto) {


        if(userRepository.findByEmail(registerDto.getEmail()).isPresent()){
            throw new UserEmailAlreadyExistException();
        }

        if(registerDto.getRegion().getAddress().isEmpty()|| registerDto.getRegion().getDepth_1().isEmpty() || registerDto.getRegion().getDepth_2().isEmpty() || registerDto.getRegion().getDepth_3().isEmpty()){
            throw new IllegalArgumentException("지역을 입력해주세요.");
        }


        User user = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .name(registerDto.getName())
                .region(registerDto.getRegion())
                .roles(Collections.singletonList(Role.ROLE_USER.name()))
                .nickname(registerDto.getName())
                .birth(registerDto.getBirth())
                .class_count(0)
                .class_distance(0)
                .profile("https://plowithmebucket.s3.ap-northeast-2.amazonaws.com/default-image.png")
                .introduction("")
                .build();

        userRepository.save(user);


    }

    //로그인
    @Transactional
    public String login(LoginDto loginDto) {


        userRepository.findByEmail(loginDto.getEmail())
                .filter(it -> passwordEncoder.matches(loginDto.getPassword(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);


        return token;
    }




}
