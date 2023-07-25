package com.example.Plowithme.Controller;

import com.example.Plowithme.Dto.BoardDto;
import com.example.Plowithme.Service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        boardService.savePosting(boardDto);
        return "Board";

    }

}
