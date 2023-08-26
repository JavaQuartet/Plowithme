package com.example.Plowithme.dto.request.mypage;


import com.example.Plowithme.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageFindDto {
    private String content;
    private String senderName;
    private String receiverName;
    private String sender_profile_url;
    private String receiver_profile_url;

    public static MessageFindDto toDto(Message message) {
        return new MessageFindDto(
                message.getContent(),
                message.getSender().getName(),
                message.getReceiver().getName(),message.getSender().getProfileUrl(message.getSender().getProfile()),message.getReceiver().getProfileUrl(message.getReceiver().getProfile()));
    }
}