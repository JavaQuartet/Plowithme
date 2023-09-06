package com.example.Plowithme.controller;

import com.example.Plowithme.dto.meeting.ClassDTO;

import com.example.Plowithme.dto.meeting.ClassSaveDto;
import com.example.Plowithme.dto.meeting.ClassFindDto;
import com.example.Plowithme.dto.meeting.ClassUpdateDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/class")
public class ClassController {

    private final ClassService classService;

    private final ClassRepository classRepository;

    @GetMapping("")
    @Operation(summary = "모임 리스트")
    public ResponseEntity<CommonResponse> findAll() {
        List<ClassDTO> classDTOList = classService.findAll();
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "전제 모임 리스트", classDTOList);
        log.info("전제 모임 리스트 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/region")
    @Operation(summary = "회원 지역 모임 조회")
    public ResponseEntity<CommonResponse> findClassByRegion(Pageable pageable, @CurrentUser User currentUser) {


        if (currentUser == null) {
            List<ClassDTO> classDtos = classService.findAll();

            CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "전체 모임 조회", classDtos);
            log.info("전체 모임 조회 완료");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            List<ClassDTO> classDtos = classService.findClassByRegion(pageable, currentUser);

            CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "회원 지역 기준 모임 조회", classDtos);
            log.info("회원 지역 기준 모임 조회 완료");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }


    @GetMapping("/search")
    @Operation(summary = "모임 지역 검색")
    public ResponseEntity<CommonResponse> searchStartRegion(Pageable pageable, @RequestParam("keyword") String keyword) {
        List<ClassFindDto> classFindDtos = classService.searchStartRegion(keyword, pageable);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 지역 검색 완료", classFindDtos);
        log.info("모임 지역 검색 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("")
    @Operation(summary = "모임 저장")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody ClassSaveDto classSaveDto, @CurrentUser User user) {
        ClassEntity classEntity = classService.saveClass(classSaveDto, user);

        classService.participant(classEntity, user);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "모임 저장 완료", classEntity.getId()); // 생성된 모임 id 반환
        log.info("전제 모임 리스트 조회 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{classId}")
    @Operation(summary = "모임 상세")
    public ResponseEntity<CommonResponse> findById(@PathVariable("classId") Long id, @CurrentUser User user) {// 모임 세부정보로 이동
        ClassDTO classDTO = classService.findById(id);

        if (user == null) {
            CommonResponse response = new CommonResponse(HttpStatus.UNAUTHORIZED.value(), "로그인 안한 유저", classDTO);
            log.info("로그인 안한 유저 조회 완료");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (user.getId().equals(classDTO.getMaker_id())) {
            CommonResponse response = new CommonResponse(HttpStatus.ACCEPTED.value(), "내가 만든 모임 이동", classDTO);
            log.info("내가 만든 모임 조회 완료");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }

        for (ClassParticipantsEntity classParticipantsEntity : classDTO.getClassParticipantsEntityList()) {
            if (user.getId() == classParticipantsEntity.getUserid()) {
                CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "참여한 모임 세부정보 이동", classDTO);
                log.info("참여한 모임 세부정보 조회 완료");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "참여 안한모임 세부정보 이동", classDTO);
        log.info("참여한 모임 세부정보 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @PostMapping("/unjoin/{classId}")
    @Operation(summary = "모임 나가기")
    public ResponseEntity<CommonResponse> unjoinClass(@PathVariable("classId") Long id, @CurrentUser User user) {

        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));

        for (ClassParticipantsEntity classParticipantsEntity : classEntity.getClassParticipantsEntityList()) {
            if (user.getId() == classParticipantsEntity.getUserid()) {
                classService.downstatus(id);
                classService.deleteparticipant(classEntity, user);
                CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "모임 나가기");
                log.info("모임 나가기 완료");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }

        CommonResponse response = new CommonResponse(HttpStatus.BAD_REQUEST.value(), "이미 나간 모임입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PostMapping("/join/{classId}")
    @Operation(summary = "모임 참여")
    public ResponseEntity<CommonResponse> joinClass(@PathVariable("classId") Long id, @CurrentUser User user) {
        ClassDTO classDTO = classService.findById(id);

        if (user == null) {
            CommonResponse response = new CommonResponse(HttpStatus.UNAUTHORIZED.value(), "로그인 필요");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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
        log.info("모임 참여하기 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/{classId}")
    @Operation(summary = "모임 수정 저장")
    public ResponseEntity<CommonResponse> update(@Valid @RequestBody ClassUpdateDto classUpdateDto, @PathVariable("classId") Long id, @CurrentUser User user_id) {
        classService.updated(id, classUpdateDto);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 수정 완료");
        log.info("모임 수정하기 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/notice/{classId}")
    @Operation(summary = "공지사항")
    public ResponseEntity<CommonResponse> getNotice(@PathVariable("classId") Long id, @RequestBody ClassUpdateDto classUpdateDto) {
        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));
        classService.notice(classEntity, classUpdateDto.getNotice());
        classService.updated(id, classUpdateDto);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "공지 저장 완료");
        log.info("공지 저장 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{classId}")
    @Operation(summary = "모임 삭제")
    public ResponseEntity<CommonResponse> delete(@PathVariable("classId") Long id) {
        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));
        classService.delete(classEntity);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 삭제");
        log.info("모임 삭제 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/ended/{classId}")
    @Operation(summary = "모임 종료")
    public ResponseEntity<CommonResponse> endedclass(@CurrentUser User user, @PathVariable("classId") Long id) {
        ClassDTO classDTO1 = classService.findById(id);
        if (classDTO1.getStatus() == 0) {
            CommonResponse response = new CommonResponse(HttpStatus.BAD_REQUEST.value(), "이미 종료된 모임입니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            classService.end_class(classDTO1, id);
            CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 종료");
            log.info("모임 종료 완료");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


    @GetMapping("/me")
    @Operation(summary = "현재 유저 모임 조회")
    public ResponseEntity<CommonResponse> findCurrentUserClasses(@RequestParam("category") Integer category, @CurrentUser User currentUser) {

        List<ClassFindDto> classFindDtos = classService.findMyClasses(currentUser.getId(), category);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "현재 유저 모임 조회 완료", classFindDtos);
        log.info("현재 유저 모임 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}




