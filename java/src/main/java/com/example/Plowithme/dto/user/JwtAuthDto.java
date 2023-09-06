package com.example.Plowithme.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthDto {

    private String accessToken;

    private String tokenType = "Bearer";

    public JwtAuthDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
