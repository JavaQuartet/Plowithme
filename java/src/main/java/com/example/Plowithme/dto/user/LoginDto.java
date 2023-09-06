package com.example.Plowithme.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
