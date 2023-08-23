package com.example.Plowithme.service;

import com.example.Plowithme.dto.BoardDto;
import com.example.Plowithme.dto.CommentDto;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.Comment;
import com.example.Plowithme.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    //
    public void saveComment(CommentDto commentDto) {
        Comment comment=Comment.toComment(commentDto);
        commentRepository.save(comment);
    }

    //Id를 통해 comment 찾기
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("Can't find Comment with id" + id));


    }
}
