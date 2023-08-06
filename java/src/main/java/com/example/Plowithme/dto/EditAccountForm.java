package com.example.Plowithme.dto;


import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditAccountForm {

    @Pattern(regexp = "^[가-힣]{2,4}$", message = "이름을 제대로 입력해주세요.")
    private String name;

    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    private String region;

}
