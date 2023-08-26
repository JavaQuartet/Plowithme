package com.example.Plowithme.entity;

import com.example.Plowithme.dto.CommentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;
import java.time.LocalDateTime;

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
