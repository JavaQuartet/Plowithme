package com.example.Plowithme.service;

import com.example.Plowithme.dto.request.LoginDto;
import com.example.Plowithme.dto.request.RegisterDto;
import com.example.Plowithme.entity.Role;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.UserEmailAlreadyExistException;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    //회원가입
    @Transactional
    public Long register(RegisterDto registerDto) {


        if(userRepository.findByEmail(registerDto.getEmail()).isPresent()){
            throw new UserEmailAlreadyExistException();
        }

        User user = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .name(registerDto.getName())
                .region(registerDto.getRegion())
                .roles(Collections.singletonList(Role.ROLE_USER.name()))
                .build();

        userRepository.save(user);

        return user.getId();
    }

    //로그인
    @Transactional
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);


        return token;
    }




}
