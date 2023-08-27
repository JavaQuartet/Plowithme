package com.example.Plowithme.dto.request.community;

import com.example.Plowithme.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter //인스턴스 변수 반환
@Setter //인스턴스 변수를 대입하거나 수정
@ToString
@Data
@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor //필드를 모두 매개변수 라는 생성자 만들어 줌
public class CommentDto {
    private Long id;
    private String writer;
    private String contents;

    @JsonProperty("post_id")
    private Long postId;

//   setter를 이용하는 방식. 다시 변수를 설정
//   public void setId(Long id) {
//        this.id=id;
//    }

    public void setWriter(String writer) {
        this.writer=writer;
    }

    public void setContents(String contents) {
        this.contents=contents;
    }


    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setWriter(comment.getWriter());
        commentDto.setContents(comment.getContents());
        return commentDto;

    }
}
