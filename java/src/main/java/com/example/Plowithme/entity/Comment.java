package com.example.Plowithme.entity;

import com.example.Plowithme.dto.community.CommentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String writer; //작성자

    @Column
    private String contents; //내용


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE) //게시글이 삭제되면 댓글들도 같이 삭제
    private BoardEntity boardEntity;


    public static Comment toComment(CommentDto commentDto) {
        Comment comment=new Comment();
        //comment.setId(commentDto.getId());
        comment.setWriter(commentDto.getWriter());
        comment.setContents(commentDto.getContents());
        return comment;
    }


}
