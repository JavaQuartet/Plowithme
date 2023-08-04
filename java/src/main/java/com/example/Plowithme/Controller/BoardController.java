package com.example.Plowithme.Controller;

import com.example.Plowithme.dto.BoardDto;
import com.example.Plowithme.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RequestMapping
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/Board")
    private String getBoard() {
        return "Board";
    }

    @GetMapping("/Board/Posting")
    private String GoPostingForm() {
        return "Posting";
    }

    @PostMapping("/Board/Posting")
    private String savePosting(@ModelAttribute BoardDto boardDto) {
        System.out.println("boardDto=" + boardDto);
        boardService.save(boardDto);
        return "Board";

    }

    @GetMapping("/Board/list")
    public String findAll(Model model) {
       //db에서 전체 게시글 데이터를 가져와서 list.html에 보여준다
       List<BoardDto> boardDtoList=boardService.findAll();
       model.addAttribute("boardlist", boardDtoList);
       return "BoardList";
    }

    @GetMapping("/Board/list/{id}")
    public String findByPostId(@PathVariable int postId, Model model) {
        /*
        해당 게시글의 조회수를 하나 올리고
        게시글 데이터를 가져와서 BoardDetail에 출력
         */
        boardService.updatePostHits(postId);
        BoardDto boardDto = boardService.findByPostId(postId);
        model.addAttribute("board", boardDto);
        return "BoardDetail";
    }
}
