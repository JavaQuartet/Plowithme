package com.example.Plowithme.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
