package com.example.Plowithme.entity;

import com.example.Plowithme.dto.request.community.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Comment")
public class Comment extends BaseEntity  {

    @Column(name = "comment_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String writer;

    @Column
    private String contents;

    //board:comment=1:n
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;


    public static Comment toComment(CommentDto commentDto) {
        Comment comment=new Comment();
//        comment.setId(commentDto.getId());
        comment.setWriter(commentDto.getWriter());
        comment.setContents(commentDto.getContents());
        return comment;
    }
}
