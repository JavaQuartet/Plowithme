package com.example.Plowithme.Controller;

import com.example.Plowithme.dto.ClassDTO;
import com.example.Plowithme.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Class")
public class ClassController {
    private final ClassService classService;

    @GetMapping ("")
    public String findAll(Model model){
        List<ClassDTO> classDTOList = classService.findAll();
        model.addAttribute("classList", classDTOList);
        return "Class";
    }

    @GetMapping("/class_save")// 모임 만들기 화면
    public String saveForm(){return "ClassSave";}
    
    @PostMapping("")
    public String save(@ModelAttribute ClassDTO classDTO, Model model){
        System.out.println("classDTO = " + classDTO);
        classService.save(classDTO);
        List<ClassDTO> classDTOList = classService.findAll();
        model.addAttribute("classList", classDTOList);
        return "Class";
    }

    @GetMapping("/class_detail")// 모임 세부정보 화면
    public String class_detail(Model model){
        return "ClassDetail";
    }


/*    @PostMapping("/{id}")
    public String save2(@ModelAttribute ClassDTO classDTO , Model model){ // 모임 내부 설정 변경
        System.out.println("classDTO = " + classDTO);
        classService.save(classDTO);
        model.addAttribute("board", classDTO);
        return "ClassDetail";
    }*/

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
   /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        /*classService.updateHits(id);*/
        ClassDTO classDTO = classService.findById(id);
        model.addAttribute("class", classDTO);
        return "ClassDetail";
    }
}
