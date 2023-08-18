package com.example.Plowithme.dto.request.mypage;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateDto {
    @Size(max=30)
    private String nickname;

    private String profile;
}
