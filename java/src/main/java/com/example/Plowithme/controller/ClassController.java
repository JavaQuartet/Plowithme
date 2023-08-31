package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.meeting.ClassDTO;

import com.example.Plowithme.dto.request.meeting.ClassSaveDto;
import com.example.Plowithme.dto.request.meeting.ClassFindDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.dumy;
import com.example.Plowithme.entity.ClassEntity;

import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.ClassParticipantRepository;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.ClassService;
import com.example.Plowithme.service.ImageService;
import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/Class")
//@CrossOrigin(origins = "http://43.200.172.177:8080, http://localhost:3000")
public class ClassController {
    private final ClassService classService;
    private final UserService userService;
    private final ClassParticipantRepository classParticipantRepository;
    private final ClassRepository classRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;


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

    @PostConstruct
    public void initializing() {
        for (int i = 0; i < 50; i++) {

            ClassEntity classEntity = ClassEntity.builder()
                    .title("제목"+i)
                    .member_max(4)
                    .member_current(1)
                    .status(1)
                    .startRegion(dumy.nNick())
                    .end_region(dumy.nNick())
                    .description("설명"+i)
                    .start_date("1")
                    .end_date("2")
                    .build();

            classRepository.save(classEntity);
        }
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


    @GetMapping("/search")
    @Operation(summary =  "모임 지역 검색")
    public ResponseEntity<CommonResponse> searchStartRegion(Pageable pageable, @RequestParam("keyword") String keyword) {
        List<ClassFindDto> classFindDtos  = classService.searchStart_region(keyword, pageable);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 지역 검색 완료", classFindDtos );
        log.info("모임 지역 검색 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @PostMapping("")// 만든 모임 저장, Class페이지로 이동
    @Operation(summary = "모임 저장")
    public ResponseEntity<CommonResponse> save(@Valid @RequestPart(value = "classSaveDto") ClassSaveDto classSaveDto, @RequestPart(value ="file", required = false) MultipartFile file, @CurrentUser User user){
        ClassEntity classEntity = classService.saveClass(classSaveDto, user.getId());
        if(file != null){
            String imageName = imageService.saveImage(file);
            classEntity.setImage_name(imageName);
        }
        classService.participant(classEntity, user);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(),"모임 저장 완료", classEntity.getId()); // 생성된 모임 id 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    @GetMapping("/{id}")// 모임 상세
    @Operation(summary = "모임 상세")
    public ResponseEntity<CommonResponse> findById(@PathVariable("id") Long id, @CurrentUser User user) {// 모임 세부정보로 이동
        ClassDTO classDTO = classService.findById(id);
        Optional<User> class_maker = userRepository.findById(classDTO.getMaker_id());

        for(ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()){
            if (user.getId() == classParticipantsEntity.getUserid()){
                CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"참여한 모임 세부정보 이동", classDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"참여 안한모임 세부정보 이동", classDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/{id}")// 참여하기 참여취소 버튼
    @Operation(summary = "참여, 참여취소")
    public ResponseEntity<CommonResponse> classbutton(@PathVariable("id") Long id, @CurrentUser User user) {
        ClassDTO classDTO = classService.findById(id);

        ClassEntity classEntity = classRepository.findById(id).get();

        for (ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()) {
            if (user.getId() == classParticipantsEntity.getUserid()) {
                classService.downstatus(id);
                classService.deleteparticipant(classEntity, user);
                CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 나가기", classDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }

        classService.updatestatus(id);
        classService.participant(classEntity, user);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 참여하기", classDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }



    @PostMapping("/joined/{id}")// 모임 수정
    @Operation(summary = "모임 수정 저장")
    public ResponseEntity<CommonResponse> update(@Valid @RequestBody ClassDTO classDTO,@PathVariable("id") Long id, @CurrentUser User user_id) {
        ClassEntity classEntity = classRepository.findById(id).get();
        ClassDTO Class = classService.update(classEntity, user_id);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 수정 완료", Class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
        //return "redirect:/joined/" + classDTO.getId();
    }


    // 모임 수정 페이지 이동
    @GetMapping("/class_update/{id}")
    @Operation(summary = "모임 수정")
    public ResponseEntity<CommonResponse> ClassUpdateForm(@PathVariable("id") Long id){
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
    public ResponseEntity<CommonResponse> endedclass(@CurrentUser User user, @PathVariable("id") Long id, @RequestBody ClassDTO classDTO) {
        ClassEntity classEntity = classRepository.findById(id).get();
        ClassDTO Class = classService.update(classEntity, user);
        classService.end_class(Class);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 종료");

        /*classService.delete(id);*/
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
