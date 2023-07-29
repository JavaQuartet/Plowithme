package com.example.Plowithme.Dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String birth;

}

