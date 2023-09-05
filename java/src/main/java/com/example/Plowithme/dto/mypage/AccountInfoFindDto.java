package com.example.Plowithme.dto.mypage;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInfoFindDto {

    private String name;

    private String email;

    private String address;
}
