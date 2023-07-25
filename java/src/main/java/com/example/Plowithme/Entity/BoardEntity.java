package com.example.Plowithme.Entity;

import com.example.Plowithme.Dto.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//db 테이블 역할을 하는 클래스
@Entity
@Table(name = "board")
@Getter
@Setter
public class BoardEntity extends BaseEntity {

    @Id //pk 컬럼 지정. 필수
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(length = 50) //크기 50, not null 지정
    private String postTitle;

    @Column(length = 500) //크기 500, null 가능
    private String postContents;

    /* 시간에 관한 것은 bastEntity에 별도로 담음. baseEntity에 담았는데 주석처리한 것들을 실행시키면 에러남
     @Column
    private String postCreateDate;

    @Column
    private String postUpdateDate;
    */

    @Column
    private String postCategory;

    @Column
    private String postImage;

    @Column
    private String postImagePath;

    //엔티티 객체를 객체로 만들어서 호출하는 게 아닌 그냥 클래스 메소드로 정의
    public static BoardEntity toBoardEntity(BoardDto boardDto) {
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setPostTitle(boardDto.getPostTitle());
        boardEntity.setPostContents(boardDto.getPostContents());
        boardEntity.setPostCategory(boardDto.getPostCategory());
        boardEntity.setPostImage(boardDto.getPostImage());
        boardEntity.setPostImagePath(boardDto.getPostImagePath());
        //dto에 담긴 것을 entity로 넘김(변환)
        //에러가 나거나 값이 생각한 값이 아니면 이 부분에서 문제가 있을 가능성 큼
        return boardEntity;
    }
}
