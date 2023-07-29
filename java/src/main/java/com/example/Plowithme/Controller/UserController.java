package com.example.Plowithme.Controller;

import com.example.Plowithme.Dto.UserForm;
import com.example.Plowithme.Entity.User;
import com.example.Plowithme.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    //회원등록
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
        user.setRegion(form.getAddress());
        user.setBirth(form.getBirth());

        userService.join(user);
        return "redirect:/login";
    }
//
//    //회원 수정
//    @GetMapping("/edit")
//    public String editForm(@ModelAttribute UserForm form) {
//        return "users/editUserForm";
//    }
//
//    @PostMapping("/edit")
//    public String edit(@Valid @ModelAttribute UserForm form, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "users/editUserForm";
//        }
//        User user = new User();
//        user.setEmail(form.getEmail());
//        user.setPassword(form.getPassword());
//        user.setName(form.getName());
//        user.setRegion(form.getAddress());
//        user.setBirth(form.getBirth());
//
//        userService.join(user);
//        return "redirect:/mypage";
//    }
//
//    @PostMapping("/mypage")
//



//    //회원 조희
//    @GetMapping("/list")
//    public String findAll(Model model) {
//        List<User> userList = userService.findUsers();
//        model.addAttribute("userList", userList);
//        return "userListForm";
//    }
//
//    @GetMapping("/list/{id}")
//    public String findById(@PathVariable Long id, Model model) {
//        User user = userService.findOne(id);
//        model.addAttribute("user", user);
//        return "detail";
//    }

//    //회원 삭제
//    @GetMapping("/delete/{id}")
//    public String deleteById(@PathVariable Long id) {
//        userService.deleteById(id);
//        return "redirect:/user/";
//    }
//
}