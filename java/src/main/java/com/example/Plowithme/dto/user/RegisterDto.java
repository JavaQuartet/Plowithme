package com.example.Plowithme.dto.user;

import com.example.Plowithme.entity.Region;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class RegisterDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @Size(max = 30)
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="[a-zA-Z1-9]{6,15}", message = "영어와 숫자를 포함해서 6~15자리 이내로 입력해주세요.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요.")
    @Size(min=2, max = 10)
    private String name;

    private Region region;

    @NotEmpty(message = "생일을 입력해주세요.")
    private String birth;


}
