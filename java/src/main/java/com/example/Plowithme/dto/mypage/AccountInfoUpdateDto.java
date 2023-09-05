package com.example.Plowithme.dto.mypage;


import com.example.Plowithme.entity.Region;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountInfoUpdateDto {

    @Pattern(regexp="[a-zA-Z1-9]{6,15}", message = "영어와 숫자를 포함해서 6~15자리 이내로 입력해주세요.")
    private String password;

    private Region region;

}
