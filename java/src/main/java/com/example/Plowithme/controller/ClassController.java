package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.meeting.ClassDTO;

import com.example.Plowithme.dto.request.meeting.ClassSaveDto;
import com.example.Plowithme.dto.request.meeting.ClassFindDto;
import com.example.Plowithme.dto.request.meeting.ClassUpdateDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.dumy;
import com.example.Plowithme.entity.ClassEntity;

import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.ClassParticipantRepository;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.ClassService;
import com.example.Plowithme.service.ImageService;
import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/class")
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


   // @GetMapping("")// 모임 불러오기
    @Operation(summary = "모임 리스트")
    public ResponseEntity<CommonResponse> findAll(Model model) {
        List<ClassDTO> classDTOList = classService.findAll();
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "전제 모임 리스트", classDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




//    @PostConstruct
//    public void initializing() {
//        for (int i = 0; i < 50; i++) {
////            ClassSaveDto classSaveDto = ClassSaveDto.builder()
//
//
//            ClassEntity classEntity = ClassEntity.builder()
//                    .title("제목" + i)
//                    .member_max(4)
//                    .startRegion("경기도 부천시 역곡동 산43-1 가톨릭대학교 성심교정")
//                    .end_region("경기도 부천시 괴안동 113-8 역곡유림빌딩")
//                    .description("설명" + i)
//                    .start_date("2023/03/04")
//                    .end_date("2023/03/04")
//                    .start_day(5)
//                    .start_month(7)
//                    .start_year(2000)
//                    .distance(0.0)
//                    .image_name("default-image.jpeg")
//                    .maker_id((long) i)
//                    .build();
//
//            classService.saveClass()
//            ClassEntity classEntity = classService.saveClass(classSaveDto, user.getId());
//
//            User user = userRepository.findById((long) i).get();
//            classService.participant(classEntity, user);
//            findById(user)
//        }
//    }


//            ClassEntity classEntity = classService.saveClass(classSaveDto,(long)i);
//            .findById(i)
//            classService.participant(classEntity, user);
//
//
//                    .title("제목"+i)
//
//
//
//        }
//    }


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


    @GetMapping("")
    @Operation(summary = "회원 지역 모임 조회")
    public ResponseEntity<CommonResponse> findClassByRegion(Pageable pageable, @CurrentUser User currentuser) {
        List<ClassDTO> classDtos  = classService.findClassByRegion(pageable, currentuser);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "전제 모임 리스트", classDtos);
        log.info("회원 지역 모임 조회");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/search")
    @Operation(summary =  "모임 지역 검색")
    public ResponseEntity<CommonResponse> searchStartRegion(Pageable pageable, @RequestParam("keyword") String keyword) {
        List<ClassFindDto> classFindDtos  = classService.searchStartRegion(keyword, pageable);
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




    @GetMapping("/{classId}")// 모임 상세
    @Operation(summary = "모임 상세")
    public ResponseEntity<CommonResponse> findById(@PathVariable("classId") Long id, @Nullable @CurrentUser User user){// 모임 세부정보로 이동
        ClassDTO classDTO = classService.findById(id);
        /*User class_maker = userRepository.findById(classDTO.getMaker_id()).orElseThrow(() -> new IllegalArgumentException());;*/

        if (user == null){
            CommonResponse response = new CommonResponse(HttpStatus.BAD_REQUEST.value(),"로그인 안한 유저", classDTO);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (user.getId().equals(classDTO.getMaker_id())) {
            CommonResponse response = new CommonResponse(HttpStatus.ACCEPTED.value(), "내가 만든 모임 이동", classDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }
        for (ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()) {
            if (user.getId() == classParticipantsEntity.getUserid()) {
                CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "참여한 모임 세부정보 이동", classDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "참여 안한모임 세부정보 이동", classDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);



    }


/*
    @PostMapping("/{id}")// 참여하기 참여취소 버튼
    @Operation(summary = "참여, 참여취소")
    public ResponseEntity<CommonResponse> classbutton(@PathVariable("id") Long id, @CurrentUser User user) {
        ClassDTO classDTO = classService.findById(id);

        ClassEntity classEntity = classRepository.findById(id).get();

        for (ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()) {
            if (user.getId() == classParticipantsEntity.getUserid()) {
                classService.downstatus(id);
                classService.deleteparticipant(classEntity, user);
                CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "모임 나가기");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }

        classService.updatestatus(id);
        classService.participant(classEntity, user);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 참여하기");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
*/

    @PostMapping("/unjoin/{classId}")
    @Operation(summary = "모임 나가기")
    public ResponseEntity<CommonResponse> unjoinClass(@PathVariable("classId") Long id, @CurrentUser User user) {

        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));
        classService.downstatus(id);
        classService.deleteparticipant(classEntity, user);
        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "모임 나가기");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/join/{classId}")
    @Operation(summary = "모임 참여")
    public ResponseEntity<CommonResponse> joinClass(@PathVariable("classId") Long id, @CurrentUser User user){
        ClassDTO classDTO = classService.findById(id);

        if (user == null){
            CommonResponse response = new CommonResponse(HttpStatus.ACCEPTED.value(), "로그인 필요");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
/*            CommonResponse response = new CommonResponse(HttpStatus.NO_CONTENT.value(),"로그인 안함 -> 로그인 페이지로 이동", classDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);*/
        }

        for (ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()) {
            if (user.getId() == classParticipantsEntity.getUserid()) {
                CommonResponse response = new CommonResponse(HttpStatus.BAD_REQUEST.value(), "이미 참여했습니다");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));
        classService.updatestatus(id);
        classService.participant(classEntity, user);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 참여하기");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 모임 수정 페이지 이동
/*    @GetMapping("/{id}")
    @Operation(summary = "모임 수정")
    public ResponseEntity<CommonResponse> ClassUpdateForm(@PathVariable("id") Long id){
        ClassDTO classDTO = classService.findById(id);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 수정", classDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }*/

    @PatchMapping("/{classId}")// 모임 수정
    @Operation(summary = "모임 수정 저장")
    public ResponseEntity<CommonResponse> update(@Valid @RequestPart(value ="classUpdateDto") ClassUpdateDto classUpdateDto ,@PathVariable("classId") Long id, @CurrentUser User user_id, @RequestPart(value ="file", required = false) MultipartFile file) {
        ClassDTO updatedClass = classService.updated(id, classUpdateDto);
        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));

        if(file != null){
            String imageName = imageService.updateImage(file, classEntity.getImage_name());
            classEntity.setImage_name(imageName);
        }

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
        //return "redirect:/joined/" + classDTO.getId();
    }

    @PatchMapping("/notice/{classId}")
    @Operation(summary = "공지사항")
    public ResponseEntity<CommonResponse> getNotice(@PathVariable("classId") Long id, @RequestBody ClassUpdateDto classUpdateDto){
        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));
        classService.notice(classEntity, classUpdateDto.getNotice());
        classService.updated(id, classUpdateDto);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"공지 저장 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //모임 삭제
    @DeleteMapping ("/{classId}")
    @Operation(summary = "모임 삭제")
    public ResponseEntity<CommonResponse> delete(@PathVariable("classId") Long id) {
        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));
        classService.delete(classEntity);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"모임 삭제");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    //모임종료
    @PatchMapping("/ended/{classId}")
    @Operation(summary = "모임 종료")
    public ResponseEntity<CommonResponse> endedclass(@CurrentUser User user, @PathVariable("classId") Long id, @RequestBody ClassUpdateDto classUpdateDto) {
        ClassDTO classDTO1 = classService.findById(id);
        if(classDTO1.getStatus() == 0){
            CommonResponse response = new CommonResponse(HttpStatus.BAD_REQUEST.value(), "이미 종료된 모임입니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            classService.updated(id, classUpdateDto);
            classService.end_class(classDTO1, classUpdateDto.getDistance(), id);
            CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 종료");

            /*classService.delete(id);*/
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
