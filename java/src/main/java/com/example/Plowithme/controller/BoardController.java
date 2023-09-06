package com.example.Plowithme.controller;

import com.example.Plowithme.dto.community.BoardDto;
import com.example.Plowithme.dto.community.BoardSaveDto;
import com.example.Plowithme.dto.community.BoardUpdateDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.BoardRepository;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.BoardService;
import com.example.Plowithme.service.CommentService;
import com.example.Plowithme.service.ImageService;
import com.example.Plowithme.service.UserService;
import com.example.Plowithme.specification.BoardSpecifications;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RequestMapping
@Controller
@RequiredArgsConstructor
@Slf4j
@Tag(name = "커뮤니티 페이지/게시글")
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final ImageService imageService;


    @PostMapping(value = "/board/posting")
    @Operation(summary = "커뮤니티 게시글 등록")
    private ResponseEntity<CommonResponse> savePosting(@CurrentUser User currentUser, @Valid @ModelAttribute BoardSaveDto boardSaveDto) {

        boardService.save(currentUser, boardSaveDto);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "게시글 생성 성공");
        log.info("게시글 등록 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/board/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<CommonResponse> delete(@CurrentUser User currentUser, @PathVariable("postId") Long postId) {
        boardService.delete(currentUser, postId);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "게시글 삭제 성공", null);
        log.info("게시글 삭제 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/board/{postId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<CommonResponse> update(@PathVariable("postId") Long postId, @CurrentUser User currentUser, @Valid @ModelAttribute BoardUpdateDto boardUpdateDto) {
        BoardEntity boardEntity = boardRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("못 찾음"));
        boardService.updatePost(currentUser, boardEntity, boardUpdateDto);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "게시글 수정 성공");
        log.info("게시글 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/board")
    @Operation(summary = "커뮤니티 전체 페이지 조회")
    public ResponseEntity<CommonResponse> findAll(@RequestParam(name = "category", required = false) List<Integer> categories) {

        Specification<BoardEntity> spec = Specification.where(null);
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(BoardSpecifications.withCategory(categories));
        }
        List<BoardDto> boardDtos = boardService.getFilter(spec);

        List<BoardDto> boardDtoList = boardService.findAll();

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "커뮤니티 전체 게시글 조회 성공", boardDtoList);
        log.info("커뮤니티 전체 페이지 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/board/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<CommonResponse> findByPostId(@PathVariable("postId") Long postId) {

        boardService.updatePostHits(postId);
        BoardDto boardDto = boardService.findByPostId(postId);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "게시글 상세 조회 성공", boardService.findByPostId(postId));
        log.info("게시글 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}