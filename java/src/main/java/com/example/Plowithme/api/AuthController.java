package com.example.Plowithme.api;


import com.example.Plowithme.dto.JoinDto;
import com.example.Plowithme.dto.LoginDto;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<String> saveUser(@Valid @ModelAttribute JoinDto dto, BindingResult bindingResult) {
        log.info("회원 가입 시작");

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원 가입 실패");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setRegion(dto.getRegion());
        user.setBirth(dto.getBirth());
        userService.join(user);
        log.info("회원 가입 성공");
        return ResponseEntity.ok("회원 가입 성공");

    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @ModelAttribute LoginDto dto, BindingResult bindingResult,
                                   HttpServletRequest request) {
        log.info("로그인 시작");

        User user = userService.login(dto.getEmail(), dto.getPassword());

        if (user == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 비밀번호가 맞지 않습니다.");
        }

        //로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        log.info("로그인 성공");
        return ResponseEntity.ok("로그인 성공");

    }


    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        log.info("로그아웃 시작");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃 성공");
        return ResponseEntity.ok("로그인 성공");
    }


}
