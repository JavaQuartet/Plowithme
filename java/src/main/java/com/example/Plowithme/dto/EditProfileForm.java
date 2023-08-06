package com.example.Plowithme.dto;

import com.example.Plowithme.entity.Profile;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditProfileForm {

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
    private String nickname;

    private Profile profile_image;
}
