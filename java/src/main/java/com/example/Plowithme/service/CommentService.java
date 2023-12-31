package com.example.Plowithme.service;

import com.example.Plowithme.dto.community.CommentDto;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.Comment;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.CommentException;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.BoardRepository;
import com.example.Plowithme.repository.CommentRepository;
import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;


    public void saveComment(Long postId, User currentUser, CommentDto commentDto) {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });

        if(!user.getId().equals(currentUser.getId())){
            throw new CommentException("접근 권한이 없습니다.");
        }


        BoardEntity optionalBoardEntity= boardRepository.findById(postId).orElseThrow(() -> {
            return new ResourceNotFoundException("포스트를 찾을 수 없습니다.");
        });

        Comment comment=Comment.builder()
                .writer(currentUser.getNickname())
                .contents(commentDto.getContents())
                .boardEntity(optionalBoardEntity)
                .build();
        commentRepository.save(comment);
    }

    //Id를 통해 comment 찾기
    public CommentDto findById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment= optionalComment.get();
            CommentDto commentDto = CommentDto.toCommentDto(comment);
            return commentDto;
        } else {
            return null;
        }


    }

    //댓글 삭제
    public void deleteComment(User currentUser, Long id) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니다."));

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        commentRepository.deleteById(id);
    }


    //댓글 목록 조회
    public List<CommentDto> findAllCommentOfPost(Long postId) {

        BoardEntity optionalBoardEntity= boardRepository.findById(postId).orElseThrow(() -> {
            return new ResourceNotFoundException("게시글을 찾을 수 없습니다.");
        });

        BoardEntity boardEntity=boardRepository.findById(postId).get();
        List<Comment> commentList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);


        List<CommentDto> commentDtoList=new ArrayList<>();
        for (Comment comment : commentList) {
            CommentDto commentDto=CommentDto.toCommentDto(comment);
            commentDtoList.add(commentDto);
        } return commentDtoList;
    }
}
