package com.example.Plowithme.dto.community;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor //기본 생성자
@AllArgsConstructor  //모든 필드를 매개변수로 하는 생성자
@ToString
public class BoardUpdateDto {
    private String title;
    private String contents;
    private Integer category;

}
