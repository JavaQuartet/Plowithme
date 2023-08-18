package com.example.Plowithme.dto;

import com.example.Plowithme.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

//DTO(Data Transfer Object), VO, Bean.
//주고받을 때 파라미터가 여러개인데 그걸 하나의 것에 담아서 보내기 위해 만들어짐(?)
@Getter
@Setter
@NoArgsConstructor //기본 생성자
@AllArgsConstructor  //모든 필드를 매개변수로 하는 생성자
@ToString
public class BoardDto {
    private Long id;
    private String postTitle;
    private String postContents;
    private String postCategory;
    private String postImage;
    private String postImagePath;
    private LocalDateTime postCreateDate;
    private LocalDateTime postUpdateDate;
    private long postHits; //조회수

    //entity를 dto로 변환하는 함수
    public static BoardDto toboardDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(boardEntity.getPostId());
        boardDto.setPostTitle(boardEntity.getPostTitle());
        boardDto.setPostContents(boardEntity.getPostContents());
        boardDto.setPostHits(boardEntity.getPostHits());
        boardDto.setPostCreateDate(boardEntity.getPostCreateDate());
        //boardDto.setPostUpdateDate(boardEntity.getPostUpdateDate());
        //boardDto.setPostCategory(boardEntity.getPostCategory());

        return boardDto;

    }

}
