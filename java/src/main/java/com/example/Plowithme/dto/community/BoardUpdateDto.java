package com.example.Plowithme.dto.community;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardUpdateDto {

    private String title;

    private String contents;

    private Integer category;

}
