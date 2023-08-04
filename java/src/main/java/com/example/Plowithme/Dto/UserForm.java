package com.example.Plowithme.Dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserForm {

    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String region;


    @NotEmpty(message = "생일을 입력해주세요.")
    private String birth;

}

