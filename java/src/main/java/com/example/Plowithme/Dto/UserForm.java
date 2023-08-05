package com.example.Plowithme.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "이름을 제대로 입력해주세요.")
    private String name;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String region;


    @NotEmpty(message = "생일을 입력해주세요.")
    private String birth;

}

