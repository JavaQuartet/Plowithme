package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.community.BoardDto;
import com.example.Plowithme.dto.request.community.BoardSaveDto;
import com.example.Plowithme.dto.request.community.BoardUpdateDto;
import com.example.Plowithme.dto.request.mypage.ProfileFindDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.CommentException;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.BoardRepository;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.BoardService;
import com.example.Plowithme.service.CommentService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://43.200.172.177:8080, http://localhost:3000")
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

    @PostMapping(value = "/board/postingImage")
    @Operation(summary = "커뮤니티 게시글 중 이미지 등록하는 기능")
    private ResponseEntity<CommonResponse> savePosting(@CurrentUser User currentUser, MultipartFile image) {
        if(!image.isEmpty()) {
            try {
                boardService.saveImage(currentUser, image);
            } catch (Exception e) {
                throw new RuntimeException("이미지 파일을 불러오는 것에 실패했습니다.");
            }
        } else return null;

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(),"이미지 업로드 성공");
        log.info("이미지 업로드 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping(value = "/board/postingImage")
//    @Operation(summary = "게시글 이미지 조회 기능")
//    private ResponseEntity<CommonResponse> getImage(BoardDto boardDto) {
//
//        BoardDto ImageDto=boardService.showImage(boardDto);
//
//        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"이미지 조회 성공", ImageDto);
//        log.info("이미지 조회 완료");
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    @DeleteMapping("/board/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<CommonResponse> delete(@CurrentUser User currentUser, @PathVariable("postId") Long postId) {
        boardService.delete(currentUser, postId);

        CommonResponse response= new CommonResponse(HttpStatus.OK.value(), "게시글 삭제 성공", null);
        //디비에 값을 저장하는 거라 저장한 값을 보여줄 필요없고 저장되었다는 결과만 반환해주면 됨.
        log.info("게시글 삭제 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/board/{postId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<CommonResponse> update(@PathVariable("postId") Long postId, @CurrentUser User currentUser, @RequestBody BoardUpdateDto boardUpdateDto) {
       // BoardDto boardDto = boardService.findByPostId(postId);
        BoardEntity boardEntity = boardRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("못 찾음"));
        boardService.updatePost(currentUser, boardEntity, boardUpdateDto);


        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"게시글 수정 성공");
        log.info("게시글 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

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


//    @GetMapping("/board/{postId}/writerPage")
//    public ResponseEntity<CommonResponse> findProfile(@PathVariable("postId") Long postId, BoardDto boardDto, User user) {
//
//        BoardDto forProfileDto=boardService.findByPostId(postId);
//        ProfileFindDto profileFindDto = boardService.findWriterProfile(forProfileDto.getWriterId());
//
//        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "프로필 조회 완료", profileFindDto);
//        log.info("프로필 조회 완료");
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getProfile() + "\"").body(response);
//    }

}

//페이지 말고 데이터 조회는 필요하다. 페이지 조회 관련한 거는 삭제.
//컨트롤러에서 뷰에 전달할 때 모델을 쓰지 API에서는 쓰지 않는다.