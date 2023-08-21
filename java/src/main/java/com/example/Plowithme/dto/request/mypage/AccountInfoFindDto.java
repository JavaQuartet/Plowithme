package com.example.Plowithme.dto.request.mypage;

import com.example.Plowithme.entity.Region;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInfoFindDto {

    private String name;

    private String email;

    private String address;
}
