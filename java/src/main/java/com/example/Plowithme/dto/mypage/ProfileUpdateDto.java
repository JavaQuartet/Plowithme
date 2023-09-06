package com.example.Plowithme.dto.mypage;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateDto {
    @Size(min=2, max = 10)
    private String nickname;

    @Size(max=100)
    private String introduction;
}
