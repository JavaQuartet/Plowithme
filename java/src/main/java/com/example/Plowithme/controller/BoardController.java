package com.example.Plowithme.controller;

import com.example.Plowithme.dto.BoardDto;
import com.example.Plowithme.dto.request.LoginDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RequestMapping
@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

//    @GetMapping("/board")
//    @Operation(summary = "커뮤니티 페이지 조회")
//    private ResponseEntity <CommonResponse> getBoard() {
//        CommonResponse response=new CommonResponse(HttpStatus.OK.value(), "커뮤니티 페이지 조회 성공");
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

//    @GetMapping("/board/posting")
//    private String GoPostingForm() {
//        return "posting";
//    }

    @PostMapping(value = "/board/posting")
    @Operation(summary = "커뮤니티 게시글 등록")
    private ResponseEntity<CommonResponse> savePosting(@RequestBody @ModelAttribute BoardDto boardDto) {
        System.out.println("boardDto=" + boardDto);
        boardService.save(boardDto);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(),"게시글 생성 성공");
        log.info("게시글 등록 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/board/list")
    public String findAll(Model model) {
       //db에서 전체 게시글 데이터를 가져와서 list.html에 보여준다
       List<BoardDto> boardDtoList=boardService.findAll();
       model.addAttribute("boardlist", boardDtoList);
       return "list";
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<CommonResponse> findByPostId(@PathVariable Long id) {
        /*
        해당 게시글의 조회수를 하나 올리고
        게시글 데이터를 가져와서 BoardDetail에 출력
         */
        boardService.updatePostHits(id);
        BoardDto boardDto = boardService.findByPostId(id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"게시글 상세 조회 성공", boardService.findByPostId(id));
        log.info("게시글 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

//페이지 말고 데이터 조회는 필요하다. 페이지 조회 관련한 거는 삭제.
//컨트롤러에서 뷰에 전달할 때 모델을 쓰지 API에서는 쓰지 않는다.