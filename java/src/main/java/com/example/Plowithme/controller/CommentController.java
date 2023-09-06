package com.example.Plowithme.controller;

import com.example.Plowithme.dto.community.CommentDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.BoardRepository;
import com.example.Plowithme.repository.CommentRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.CommentService;
import com.example.Plowithme.service.UserService;
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

import java.util.List;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@Tag(name = "커뮤니티 페이지/댓글")
@RequestMapping("/board")
public class CommentController {

    @Autowired
    private final CommentService commentService;

    private final UserService userService;

    private final CommentRepository commentRepository;

    private final BoardController boardController;

    private final BoardRepository boardRepository;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "댓글 작성")
    //컨트롤러에서 바디를 자바객체로 받기 위해서는 @restbody 를 반드시 명시해야함
    public ResponseEntity<CommonResponse> save(@Valid @PathVariable("postId") Long postId, @CurrentUser User currentUser, @RequestBody CommentDto commentDto) {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(id);
//        commentDto.setContents(contents);
//        commentSaveDto.setWriter(currentUser.getName());
        commentService.saveComment(postId, currentUser, commentDto);

        CommonResponse response= new CommonResponse(HttpStatus.CREATED.value(), "댓글 등록 성공");
        //디비에 값을 저장하는 거라 저장한 값을 보여줄 필요없고 저장되었다는 결과만 반환해주면 됨.
        log.info("댓글 등록 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/comments/{id}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<CommonResponse> delete(@CurrentUser User currentUser, @RequestBody @PathVariable("id") Long id) {
        commentService.deleteComment(currentUser, id);

        CommonResponse response= new CommonResponse(HttpStatus.OK.value(), "댓글 삭제 성공", commentRepository.findById(id));
        //디비에 값을 저장하는 거라 저장한 값을 보여줄 필요없고 저장되었다는 결과만 반환해주면 됨.
        log.info("댓글 삭제 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "댓글 목록 가져오기")
    public ResponseEntity<CommonResponse> getComments(@Valid @PathVariable("postId") Long postId) {

       // List<CommentDto> commentDtoList=commentService.findAllCommentOfPost(postId);

        CommonResponse response= new CommonResponse(HttpStatus.OK.value(), "댓글 목록 조회 성공", commentService.findAllCommentOfPost(postId));
        //디비에 값을 저장하는 거라 저장한 값을 보여줄 필요없고 저장되었다는 결과만 반환해주면 됨.
        log.info("댓글 목록 조회 성공");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}