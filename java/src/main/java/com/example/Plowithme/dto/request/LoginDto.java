package com.example.Plowithme.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
}
