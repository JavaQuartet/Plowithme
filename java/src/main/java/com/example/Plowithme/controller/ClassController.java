package com.example.Plowithme.controller;

import com.example.Plowithme.dto.ClassDTO;

import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.ClassEntity;

import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.ClassParticipantRepository;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.ClassService;
import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;
    private final ClassParticipantRepository classParticipantRepository;
    private final ClassRepository classRepository;



/*
    @GetMapping ("")// 모임 불러오기
    public String findAll(Model model){
        List<ClassDTO> classDTOList = classService.findAll();
        model.addAttribute("classList", classDTOList);
        return "Class";
    }
*/


    @GetMapping ("")// 모임 불러오기
    @Operation(summary = "모임 리스트")
    public ResponseEntity<CommonResponse> findAll(Model model){
        List<ClassDTO> classDTOList = classService.findAll();
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"전제 모임 리스트", classDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




/*    @GetMapping("/class_save")// 모임 만들기 화면
    public String saveForm(){return "ClassSave";}*/


/*    @PostMapping("")// 만든 모임 저장, Class페이지로 이동
    public String save(@ModelAttribute ClassDTO classDTO, @CurrentUser User user, Model model) throws IOException {
        System.out.println("classDTO = " + classDTO);
userService.findOne();


        classService.save(classDTO, user);
        classService.participant(classDTO, user);

        List<ClassDTO> classDTOList = classService.findAll();
        model.addAttribute("classList", classDTOList);
        return  "Class";
    }*/

    @PostMapping("")// 만든 모임 저장, Class페이지로 이동
    @Operation(summary = "모임 저장")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody ClassDTO classDTO, @CurrentUser User user) throws IOException {
        System.out.println("classDTO = " + classDTO);



        ClassEntity classEntity = classService.save(classDTO, user);
        classService.participant(classEntity, user);

        List<ClassDTO> classDTOList = classService.findAll();
        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(),"모임 저장 완료", classDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // 수정 필요 공지
/*    @PostMapping("/{id}")
    public String save2(@ModelAttribute ClassDTO classDTO , Model model){ // 공지 설정
        System.out.println("classDTO = " + classDTO);
        classService.save(classDTO);
        model.addAttribute("class", classDTO);
        return "ClassDetail";
    }*/










/*    @GetMapping("/{id}")// 참여 안한 모임
    public ResponseEntity<CommonResponse> findById(@PathVariable Long id, Model model, @CurrentUser User user) {// 모임 세부정보로 이동
        ClassDTO classDTO = classService.findById(id);

        if(classParticipantRepository.findById(user.getId()).isPresent()){
            classService.unjoinedclass(id);
            classService.downstatus(id);
        }

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 세부정보 이동", classDTO);
        *//*model.addAttribute("class", classDTO);*//*
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }*/


    @GetMapping("/{id}")// 모임 상세
    @Operation(summary = "모임 상세")
    public ResponseEntity<CommonResponse> findById(@PathVariable("id") Long id, @CurrentUser User user) {// 모임 세부정보로 이동
        ClassDTO classDTO = classService.findById(id);
        for(ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()){
            if (user.getId() == classParticipantsEntity.getUserid()){
                CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"참여한 모임 세부정보 이동", classDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"참여 안한모임 세부정보 이동", classDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
/*        if(classParticipantRepository.findByUserid(user.getId()).isPresent() && classParticipantRepository.findByClassid(classDTO.getId()).isPresent()){
            CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"참여한 모임 세부정보 이동", classDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{
            CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"참여 안한모임 세부정보 이동", classDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }*/
    }

    @PostMapping("/{id}")// 참여하기 참여취소 버튼
    @Operation(summary = "참여, 참여취소")
    public ResponseEntity<CommonResponse> classbutton(@PathVariable("id") Long id, @CurrentUser User user) {
        ClassDTO classDTO = classService.findById(id);
        ClassEntity classEntity = classRepository.findById(classDTO.getId()).get();

        for(ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()){
            if (user.getId() == classParticipantsEntity.getUserid()){
                classService.downstatus(id);
                classService.deleteparticipant(classDTO, user);
                CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 나가기", classDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }

        classService.updatestatus(id);
        classService.participant(classEntity, user);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 참여하기", classDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);


/*        if(classParticipantRepository.findByUserid(user.getId()).isPresent() && classParticipantRepository.findByClassid(classDTO.getId()).isPresent()){// 모임 참여취소
            classService.downstatus(id);
            classService.deleteparticipant(classDTO, user);
            CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"참여한 모임 세부정보 이동", classDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{// 모임 참여하기
            classService.updatestatus(id);
            classService.participant(classDTO, user);
            CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 참여하기", classDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }*/
    }

/*    @GetMapping("/joined/{id}") // 참여한 모임
    public String joinedclass(@PathVariable Long id, Model model, @CurrentUser User user){

        ClassDTO classDTO = classService.findById(id);
        if(classParticipantRepository.findById(user.getId()).isPresent() == false) {
            classService.updatestatus(id);
            classService.participant(classDTO, user);
        }

        model.addAttribute("class", classDTO);
        if(user.getId().equals(classDTO.getMaker_id())){
            return "MyClass";
        }else{
            return "JoinClass";
        }
    }*/


    @PostMapping("/joined/{id}")// 모임 수정
    @Operation(summary = "모임 수정 저장")
    public ResponseEntity<CommonResponse> update(@Valid @RequestBody ClassDTO classDTO, Model model, @CurrentUser User user_id) {
        ClassDTO Class = classService.update(classDTO, user_id);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 수정 완료", Class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
        //return "redirect:/joined/" + classDTO.getId();
    }


    // 모임 수정 페이지 이동
    @GetMapping("/class_update/{id}")
    @Operation(summary = "모임 수정")
    public ResponseEntity<CommonResponse> ClassUpdateForm(@PathVariable("id") Long id, Model model){
        ClassDTO classDTO = classService.findById(id);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 수정", classDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //모임 삭제
    @DeleteMapping ("/{id}")
    @Operation(summary = "모임 삭제")
    public ResponseEntity<CommonResponse> delete(@PathVariable("id") Long id) {
        classService.delete(id);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 삭제");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    //모임종료 후기 작성 칸으로 넘어갈 수 있게끔 수정
    @PostMapping("/ended/{id}")
    @Operation(summary = "모임 종료")
    public ResponseEntity<CommonResponse> endedclass(@PathVariable("id") Long id, @CurrentUser User user, @RequestBody int distance) {

        ClassDTO classDTO = classService.findById(id);
        classDTO.setDistance(distance);
        classService.end_class(classDTO);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 종료");

        classService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
