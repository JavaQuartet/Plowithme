package com.example.Plowithme.dto.meeting;


import com.example.Plowithme.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinedClassProfileFindDto {

    private String nickname;

    private String profile_url;

    public static JoinedClassProfileFindDto toDto(User user) {
        return new JoinedClassProfileFindDto(user.getNickname(),
                user.getProfile());
    }

}
