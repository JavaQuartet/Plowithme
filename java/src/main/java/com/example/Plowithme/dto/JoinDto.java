package com.example.Plowithme.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="[a-zA-Z1-9]{6,15}", message = "비밀번호는 영어와 숫자를 포함해서 6~15자리 이내로 입력해주세요.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String region;


    @NotEmpty(message = "생일을 입력해주세요.")
    @Size(min = 8, max = 8, message = "생일은 8자리수여야 합니다.")
    private String birth;

}