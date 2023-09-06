package com.example.Plowithme.dto.community;

import java.time.LocalDateTime;

import com.example.Plowithme.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {

    private Long postId;

    private String title;

    private Long writerId;

    private String contents;

    private Integer category;

    private String imagePath;

    private LocalDateTime CreateDate;

    private LocalDateTime UpdateDate;

    private long postHits;

    public static BoardDto toboardDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setPostId(boardEntity.getPostId());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setWriterId(boardEntity.getWriterId());
        boardDto.setContents(boardEntity.getContents());
        boardDto.setPostHits(boardEntity.getPostHits());
        boardDto.setCreateDate(boardEntity.getCreateDate());
        boardDto.setImagePath(boardEntity.getImagePath());
        boardDto.setCategory(boardEntity.getCategory());

        return boardDto;

    }
}
