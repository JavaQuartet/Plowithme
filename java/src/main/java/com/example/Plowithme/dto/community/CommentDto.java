package com.example.Plowithme.dto.community;

import com.example.Plowithme.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String writer;

    private String contents;

    @JsonProperty("post_id")
    private Long postId;


    public void setWriter(String writer) {
        this.writer=writer;
    }

    public void setContents(String contents) {
        this.contents=contents;
    }


    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto=new CommentDto();
        commentDto.setWriter(comment.getWriter());
        commentDto.setContents(comment.getContents());
        commentDto.setPostId(comment.getBoardEntity().getPostId());
        return commentDto;

    }
}
