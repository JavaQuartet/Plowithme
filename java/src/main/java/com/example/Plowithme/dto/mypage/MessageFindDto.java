package com.example.Plowithme.dto.mypage;


import com.example.Plowithme.entity.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageFindDto {

    private Long massageId;

    private String content;

    private String senderNickname;

    private String receiverNickname;

    private String sender_profile_url;

    private String receiver_profile_url;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime create_date;
    public static MessageFindDto toDto(Message message) {
        return new MessageFindDto(message.getId(),
                message.getContent(),
                message.getSender().getNickname(),
                message.getReceiver().getNickname(),message.getSender().getProfile(),message.getReceiver().getProfile(), message.getCreate_date());
    }
}