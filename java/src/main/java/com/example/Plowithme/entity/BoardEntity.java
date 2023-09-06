package com.example.Plowithme.entity;

import com.example.Plowithme.dto.community.BoardDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
public class BoardEntity extends BaseEntity {

    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private Long writerId; //작성자 id

    @Column(length = 100) //크기 50
    private String title; //게시글 제목

    @Column(length = 5000) //크기 5000
    private String contents; //게시글 내용

    @Column(name="board_category")
    private Integer category; //카테고리

    @Column
    private long postHits;  //조회수

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList=new ArrayList<>();

    //user:board=1:n
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE) //게시글이 삭제되면 댓글들도 같이 삭제
    private User user;

    @Column @Nullable
    private String postImage; //게시글 이미지

    @Column @Nullable
    private String imagePath; //게시글 url


}
