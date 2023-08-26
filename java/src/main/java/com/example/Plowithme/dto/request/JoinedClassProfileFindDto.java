package com.example.Plowithme.dto.request;


import com.example.Plowithme.dto.request.mypage.MessageFindDto;
import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.Message;
import com.example.Plowithme.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinedClassProfileFindDto {

    private String nickname;
    private String profile_url;

    public static JoinedClassProfileFindDto toDto(User user) {
        return new JoinedClassProfileFindDto(user.getNickname(),
                user.getProfileUrl(user.getProfile()));
    }

}
