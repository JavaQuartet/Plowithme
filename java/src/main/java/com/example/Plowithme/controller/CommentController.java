package com.example.Plowithme.controller;

import com.example.Plowithme.dto.community.CommentDto;
import com.example.Plowithme.dto.CommonResponse;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.CommentRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@Tag(name = "커뮤니티 페이지/댓글")
@RequestMapping("/board")
public class CommentController {

    @Autowired
    private final CommentService commentService;

    private final CommentRepository commentRepository;


    @PostMapping("/{postId}/comments")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<CommonResponse> save(@Valid @PathVariable("postId") Long postId, @CurrentUser User currentUser, @RequestBody CommentDto commentDto) {
        commentService.saveComment(postId, currentUser, commentDto);

        CommonResponse response= new CommonResponse(HttpStatus.CREATED.value(), "댓글 등록 성공");
        log.info("댓글 등록 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/comments/{id}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<CommonResponse> delete(@CurrentUser User currentUser, @RequestBody @PathVariable("id") Long id) {
        commentService.deleteComment(currentUser, id);

        CommonResponse response= new CommonResponse(HttpStatus.OK.value(), "댓글 삭제 성공", commentRepository.findById(id));
        log.info("댓글 삭제 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{postId}/comments")
    @Operation(summary = "댓글 목록 가져오기")
    public ResponseEntity<CommonResponse> getComments(@Valid @PathVariable("postId") Long postId) {

        CommonResponse response= new CommonResponse(HttpStatus.OK.value(), "댓글 목록 조회 성공", commentService.findAllCommentOfPost(postId));
        log.info("댓글 목록 조회 성공");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}