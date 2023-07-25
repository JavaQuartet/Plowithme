package com.example.Plowithme.Dto;

import java.time.LocalDateTime;
import lombok.*;

//DTO(Data Transfer Object), VO, Bean.
//주고받을 때 파라미터가 여러개인데 그걸 하나의 것에 담아서 보내기 위해 만들어짐(?)
@Getter
@Setter
@NoArgsConstructor //기본 생성자
@AllArgsConstructor  //모든 필드를 매개변수로 하는 생성자
@ToString
public class BoardDto {
    private Long postId;
    private String postTitle;
    private String postContents;
    private String postCategory;
    private String postImage;
    private String postImagePath;
    private LocalDateTime postCreateDate;
    private LocalDateTime postUpdateDate;
    //private int portHits; >>조회수

}
