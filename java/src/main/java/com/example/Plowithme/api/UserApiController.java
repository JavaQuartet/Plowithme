package com.example.Plowithme.api;


import com.example.Plowithme.Dto.UserForm;
import com.example.Plowithme.Entity.User;
import com.example.Plowithme.Service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {


    private final UserService userService;

    //회원 등록
    @PostMapping("/api/v1/users")
    public CreateuserResponse saveUser(@RequestBody @Valid UserForm request) {
        log.info("회원 등록 start");
        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setRegion(request.getRegion());
        user.setBirth(request.getBirth());

        userService.join(user);

        Long id = userService.join(user);
        log.info("회원 등록 end");

        return new CreateuserResponse(id);
    }
    @Data
    static class CreateuserResponse {
        private Long id;
        public CreateuserResponse(Long id) {
            this.id = id;
        }
    }

}
