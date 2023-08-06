package com.example.Plowithme.controller;

import com.example.Plowithme.dto.ClassDTO;
import com.example.Plowithme.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Class")
public class ClassController {
    private final ClassService classService;

    @GetMapping ("")// 모임 불러오기
    public String findAll(Model model){
        List<ClassDTO> classDTOList = classService.findAll();
        model.addAttribute("classList", classDTOList);
        return "Class";
    }







    @GetMapping("/class_save")// 모임 만들기 화면
    public String saveForm(){return "ClassSave";}
    
    @PostMapping("")// 만든 모임 저장, Class페이지로 이동
    public String save(@ModelAttribute ClassDTO classDTO, Model model) throws IOException {
        System.out.println("classDTO = " + classDTO);
        classService.save(classDTO);

        List<ClassDTO> classDTOList = classService.findAll();
        model.addAttribute("classList", classDTOList);
        return  "Class";
    }







    // 수정 필요 공지
    /*@PostMapping("/{id}")
    public String save2(@ModelAttribute ClassDTO classDTO , Model model){ // 공지 설정
        System.out.println("classDTO = " + classDTO);
        classService.save(classDTO);
        model.addAttribute("class", classDTO);
        return "ClassDetail";
    }*/









    @GetMapping("/{id}")// 참여 안한 모임
    public String findById(@PathVariable Long id, Model model) {// 모임 세부정보로 이동
        ClassDTO classDTO = classService.findById(id);
/*        classService.downstatus(id);*/
        classService.unjoinedclass(id);
        model.addAttribute("class", classDTO);
        return "ClassDetail";
    }

    @GetMapping("/joined/{id}") // 참여한 모임
    public String joinedclass(@PathVariable Long id, Model model){
        ClassDTO classDTO = classService.findById(id);
        /*classService.updatestatus(id);*/
        classService.joinedclass(id);
        model.addAttribute("class", classDTO);
        return "JoinClass";
    }

    @PostMapping("/joined/{id}")
    public String update(@ModelAttribute ClassDTO classDTO, Model model) {
        ClassDTO Class = classService.update(classDTO);
        model.addAttribute("class", Class);
        return "JoinClass";
        //return "redirect:/joined/" + classDTO.getId();
    }






    // 모임 수정
    @GetMapping("/class_update/{id}")
    public String ClassUpdateForm(@PathVariable Long id, Model model){
        ClassDTO classDTO = classService.findById(id);
        model.addAttribute("classUpdate", classDTO);
        return "ClassUpdate";
    }


    //모임 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        classService.delete(id);
        return "redirect:/Class";
    }



    //모임종료
    @GetMapping("/ended/{id}")
    public String endedclass(@PathVariable Long id, Model model) {
        ClassDTO classDTO = classService.findById(id);
        model.addAttribute("ended", classDTO);
        classService.delete(id);
        return "EndClass";
    }
}
