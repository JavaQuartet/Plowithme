package com.example.Plowithme.entity;

import com.example.Plowithme.dto.CommentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Setter
@Getter
@Table(name = "Comment")
public class Comment extends BaseEntity implements Serializable  {

    @Column @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String writer;

    @Column
    private String contents;

    @Column
    private Long boardId;


    public static Comment toComment(CommentDto commentDto) {
        Comment comment=new Comment();
//        comment.setId(commentDto.getId());
        comment.setWriter(commentDto.getWriter());
        comment.setContents(commentDto.getContents());
        return comment;
    }
}
