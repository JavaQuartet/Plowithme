package com.example.Plowithme.repository;

import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    List<Comment> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);


}
