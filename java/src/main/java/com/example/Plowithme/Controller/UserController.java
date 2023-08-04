package com.example.Plowithme.Controller;
import com.example.Plowithme.Dto.EditAccountForm;
import com.example.Plowithme.Dto.EditProfileForm;
import com.example.Plowithme.Dto.UserForm;
import com.example.Plowithme.Entity.User;
import com.example.Plowithme.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    //회원 등록
    @GetMapping("/add")

    public String addForm(@ModelAttribute UserForm form) {
        return "users/addUserForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/addUserForm";
        }
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setName(form.getName());
        user.setRegion(form.getRegion());
        user.setBirth(form.getBirth());

        userService.join(user);
        return "redirect:/login";
    }
    //회원 조회
    @GetMapping("/list")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }

    //회원 계정 수정 (마이페이지)
    @GetMapping("/list/{userId}/edit-account")
    public String editAccountForm(@PathVariable("userId") Long userId, Model model) {
        User user = (User) userService.findOne(userId);
        EditAccountForm form = new EditAccountForm();
        form.setName(user.getName());
        form.setPassword(user.getPassword());
        form.setRegion(user.getRegion());
        model.addAttribute("form", form);
        return "users/editAccountForm";
    }

    @PostMapping("/list/{userId}/edit-account")
    public String editAccount(@PathVariable Long userId, @ModelAttribute("form") EditAccountForm form) {


        userService.editAccountUser(userId,  form.getName(),form.getPassword(), form.getRegion());

        return "redirect:/users/myPage";
    }


    //회원 프로필 수정 (마이페이지)
    @GetMapping("/list/{userId}/edit-profile")
    public String editProfileForm(@PathVariable("userId") Long userId, Model model) {
        User user = (User) userService.findOne(userId);
        EditProfileForm form = new EditProfileForm();
        form.setNickname(form.getNickname());
        form.setProfile_image(form.getProfile_image());
        model.addAttribute("form", form);
        return "users/editProfileFrom";
    }

    @PostMapping("/list/{userId}/edit-profile")
    public String editProfile(@PathVariable Long userId, @ModelAttribute("form") EditProfileForm form) {
        userService.editProfileUser(userId, form.getNickname(), form.getProfile_image());

        return "redirect:/users/myPage";
    }




    //회원 삭제
    @GetMapping("/list/{userId}/delete")
    public String deleteUserForm(@PathVariable("userId") Long userId, Model model) {
        userService.deleteUser(userId);
        return "redirect:/users/list";
    }


    //세션 관리
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

        return "세션 출력";
    }

}