package com.example.Plowithme.dto.request.mypage;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileFindDto {

    @Size(min=2, max = 10)
    private String nickname;

    private String profile_url;

    @Size(max=250)
    private String introduction;
}
