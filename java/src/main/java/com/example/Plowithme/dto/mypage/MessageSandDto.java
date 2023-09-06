package com.example.Plowithme.dto.mypage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageSandDto {

    @NotEmpty
    @Size(max=150)
    private String content;

    private Long receiverId;

}
