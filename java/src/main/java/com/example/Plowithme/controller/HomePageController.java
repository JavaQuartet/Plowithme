package com.example.Plowithme.controller;
import com.example.Plowithme.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomePageController {

    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        //세션에 회원 데이터가 없으면 main
        if (loginUser == null) {
            return "main";
        }

        //세션이 유지되면 로그인 홈
        model.addAttribute("user", loginUser);
        return "loginHome";
    }
}
