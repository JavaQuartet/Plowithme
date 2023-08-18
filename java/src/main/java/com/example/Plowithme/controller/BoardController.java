package com.example.Plowithme.controller;

import com.example.Plowithme.dto.BoardDto;
import com.example.Plowithme.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RequestMapping
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    private String getBoard() {
        return "board";
    }

    @GetMapping("/board/posting")
    private String GoPostingForm() {
        return "posting";
    }

    @PostMapping("/board/posting")
    private String savePosting(@ModelAttribute BoardDto boardDto) {
        System.out.println("boardDto=" + boardDto);
        boardService.save(boardDto);
        return "board";
    }

    @GetMapping("/board/list")
    public String findAll(Model model) {
        //db에서 전체 게시글 데이터를 가져와서 list.html에 보여준다
        List<BoardDto> boardDtoList= boardService.findAll();
        model.addAttribute("boardList", boardDtoList);
       return "list";
    }

    @GetMapping("/board/list/{id}")
    public String findByPostId(@PathVariable Long postId,
                               Model model) {
        /*
        해당 게시글의 조회수를 하나 올리고
        게시글 데이터를 가져와서 BoardDetail에 출력
         */
        boardService.updatePostHits(postId);
        BoardDto boardDto = boardService.findByPostId(postId);
        model.addAttribute("board", boardDto);
        return "detail";
    }

}
