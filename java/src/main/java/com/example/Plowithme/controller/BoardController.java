package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.community.BoardDto;
import com.example.Plowithme.dto.request.community.BoardSaveDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.BoardService;
import com.example.Plowithme.service.CommentService;
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

//    @GetMapping("/board")
//    @Operation(summary = "커뮤니티 페이지 조회")
//    private ResponseEntity <CommonResponse> getBoard() {
//        CommonResponse response=new CommonResponse(HttpStatus.OK.value(), "커뮤니티 페이지 조회 성공");
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//     프론트 분들이 연결해주실 거라 get 방식 작성 필요 없음
//    @GetMapping("/board/posting")
//    private String GoPostingForm() {
//        return "posting";
//    }

    @PostMapping(value = "/board/posting")
    @Operation(summary = "커뮤니티 게시글 등록")
    private ResponseEntity<CommonResponse> savePosting(@CurrentUser User currentUser, @RequestBody BoardDto boardDto) {

        System.out.println("boardDto=" + boardDto);
        boardService.save(currentUser, boardDto);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(),"게시글 생성 성공");
        log.info("게시글 등록 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/board/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<CommonResponse> delete(@Valid @PathVariable("postId") Long postId, @RequestBody Long id) {
        boardService.delete(id);

        CommonResponse response= new CommonResponse(HttpStatus.OK.value(), "게시글 삭제 성공");
        //디비에 값을 저장하는 거라 저장한 값을 보여줄 필요없고 저장되었다는 결과만 반환해주면 됨.
        log.info("게시글 삭제 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PatchMapping("/board/{postId}")
//    @Operation(summary = "게시글 수정")
//    public ResponseEntity<CommonResponse> update(@RequestBody @PathVariable("postId") Long postId, @CurrentUser User currentUser, BoardDto boardDto) {
//
//       boardService.findByPostId(postId);
//       boardService.updatePost(postId,currentUser);
//
//        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"게시글 수정 성공");
//        log.info("게시글 수정 완료");
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//
//    }

//    @PostMapping("/member/{id}")
//    public String update(@ModelAttribute MemberDto memberDto) {
//        memberService.update(memberDto);
//        return "redirect:mypage"; //+memberDto.getId();
//    }

    @GetMapping("/board")
    @Operation(summary = "커뮤니티 전체 페이지 조회")
    public ResponseEntity<CommonResponse> findAll(@RequestParam(name = "category", required = false) List<Integer> categories) {

        Specification<BoardEntity> spec = Specification.where(null);
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(BoardSpecifications.withCategory(categories));
        }
        List<BoardDto> boardDtos = boardService.getFilter(spec);

        List<BoardDto> boardDtoList=boardService.findAll();

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"커뮤니티 전체 게시글 조회 성공", boardDtoList);
        log.info("커뮤니티 전체 페이지 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/board/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<CommonResponse> findByPostId(@PathVariable("postId") Long postId) {
        /*
        해당 게시글의 조회수를 하나 올리고
        게시글 데이터를 가져와서 BoardDetail에 출력
         */

        boardService.updatePostHits(postId);
        BoardDto boardDto = boardService.findByPostId(postId);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"게시글 상세 조회 성공", boardService.findByPostId(postId));
        log.info("게시글 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

//페이지 말고 데이터 조회는 필요하다. 페이지 조회 관련한 거는 삭제.
//컨트롤러에서 뷰에 전달할 때 모델을 쓰지 API에서는 쓰지 않는다.