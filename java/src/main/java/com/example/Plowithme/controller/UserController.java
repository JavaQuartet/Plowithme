package com.example.Plowithme.controller;
import com.example.Plowithme.dto.JoinDto;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.service.UserService;
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

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
//    private final ProfileStore profileStore;

    //회원 등록
    @GetMapping("/add")

    public String addForm(@ModelAttribute JoinDto dto) {
        return "users/addUserForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute JoinDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/addUserForm";
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setRegion(dto.getRegion());
        user.setBirth(dto.getBirth());
        userService.join(user);
        return "redirect:/login/loginForm";
    }

//
//    //회원 계정 수정 (마이페이지)
//    @GetMapping("/mypage/{userId}/edit-account")
//    public String editAccountForm(@PathVariable("userId") Long userId, Model model) {
//        User user = (User) userService.findOne(userId);
//        EditAccountDto form = new EditAccountDto();
//        form.setName(user.getName());
//        form.setPassword(user.getPassword());
//        form.setRegion(user.getRegion());
//        model.addAttribute("form", form);
//        return "users/editAccountForm";
//    }
//
//    @PostMapping("/mypage/{userId}/edit-account")
//    public String editAccount(@PathVariable Long userId, @ModelAttribute("form") EditAccountDto form) {
//
//
//        userService.editUserAccount(userId,  form.getName(),form.getPassword(), form.getRegion());
//
//        return "redirect:/users/myPage";
//    }
//
//    //회원 프로필 수정 (마이페이지)
//    @GetMapping("/mypage/{userId}/edit-profile")
//    public String editProfileForm(@PathVariable("userId") Long userId, Model model) {
//        User user = (User) userService.findOne(userId);
//
//        EditProfileDto form = new EditProfileDto();
//        form.setNickname(user.getNickname());
//        form.setProfile_image(user.getProfile_image());
//
//        model.addAttribute("form", form);
//        return "users/editProfileFrom";
//    }


//    //프로필 수정
//    @PostMapping("/mypage/{userId}/edit-profile")
//    public String editProfile(@PathVariable Long userId, @ModelAttribute EditProfileForm form, RedirectAttributes redirectAttributes) throws IOException {
//        Profile attachFile = userService.storeFile(form.getProfile_image());
//        User user = userService.findOne(userId);
//        user.setNickname(form.getNickname());
//        user.setProfile_image(form.getProfile_image());
//        redirectAttributes.addAttribute("userId", user.getId());
//        return "redirect:/users/myPage";
//    }
//    //프로필 이미지
//    @ResponseBody
//    @GetMapping("/mypage/images/{profilename}")
//    public Resource downloadImage(@PathVariable String profilename) throws
//            MalformedURLException {
//        return new UrlResource("프로필:" + profileStore.getFullPath(profilename));
//    }

//    //프로필 조회
//    @GetMapping("/profiles/{id}")
//    public Resource showProfile(@PathVariable Long id) throws
//            MalformedURLException {
//        return new UrlResource("file:" + userService.showProfile(id));
//    }
//
//    //프로필 수정
//    @PutMapping("/profile/{file}")
//    public String updateProfile(@Valid EditProfileForm dto, BindingResult bindingResult, @PathVariable MultipartFile file) throws IOException {
//        log.info("Update Profile Api Start");
//        userService.storeFile(file);
//        log.info("Update Profile Api Start");
//        return "redirect:/users/editUserForm";
//    }

//    //프로필 조회
//    @PostMapping("/profiles/list")
//    public Resource showProfile(@PathVariable Long id) throws
//            MalformedURLException {
//        return new UrlResource("file:" + userService.showProfile(id));
//    }
//
//    //프로필 수정
//    @PutMapping("/profile/{file}")
//    public String updateProfile(@Valid EditProfileForm dto, BindingResult bindingResult, @PathVariable MultipartFile file) throws IOException {
//        log.info("Update Profile Api Start");
//        userService.storeFile(file);
//        log.info("Update Profile Api Start");
//        return "redirect:/users/editUserForm";
//    }


//    //회원 삭제
//    @GetMapping("/list/{userId}/delete")
//    public String deleteUserForm(@PathVariable("userId") Long userId, Model model) {
//        userService.deleteUser(userId);
//        return "redirect:/users/list";
//    }


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