package com.example.Plowithme.controller;
import com.example.Plowithme.dto.LoginDto;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginDto dto) {
        return "login/loginForm";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginDto dto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        log.info("로그인 시작");

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        User user = userService.login(dto.getEmail(), dto.getPassword());

        if (user == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        log.info("로그인 성공");
        return "redirect:" + redirectURL;

    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("로그아웃 시작");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃 성공");
        return "redirect:/";
    }


}
