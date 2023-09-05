package com.example.Plowithme.dto.community;

import java.time.LocalDateTime;

import com.example.Plowithme.entity.BoardEntity;
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
    private String title;
    private Long writerId;
    private String contents;
    private Integer category;
    private String postImage;
    private String imagePath;
    private LocalDateTime CreateDate;
    private LocalDateTime UpdateDate;
    private long postHits; //조회수

    public static BoardDto toboardDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setPostId(boardEntity.getPostId());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setWriterId(boardEntity.getWriterId());
        boardDto.setContents(boardEntity.getContents());
        boardDto.setPostHits(boardEntity.getPostHits());
        boardDto.setCreateDate(boardEntity.getCreateDate());
        //boardDto.setUpdateDate(boardEntity.getUpdateDate());
        boardDto.setCategory(boardEntity.getCategory());

        return boardDto;

    }
}
