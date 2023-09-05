package com.example.Plowithme.dto.community;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor //기본 생성자
@AllArgsConstructor  //모든 필드를 매개변수로 하는 생성자
@ToString
public class BoardSaveDto {
    private String title;
    private String contents;
    private Integer category;
    private LocalDateTime CreateDate;

    @JsonProperty("user_id")
    private Long id;
}
