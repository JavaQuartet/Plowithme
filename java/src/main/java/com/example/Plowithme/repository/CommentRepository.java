package com.example.Plowithme.repository;

import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    //select*free comment where board_id=?order by id desc;
    List<Comment> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

    //List<Comment> findByPostId(Long postId);

  //  List<Comment> findAllByPostId(Long postId);
}
