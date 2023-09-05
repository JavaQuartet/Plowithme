package com.example.Plowithme.controller;


import com.example.Plowithme.dto.meeting.JoinedClassProfileFindDto;
import com.example.Plowithme.dto.mypage.AccountInfoFindDto;
import com.example.Plowithme.dto.mypage.AccountInfoUpdateDto;
import com.example.Plowithme.dto.mypage.ProfileFindDto;
import com.example.Plowithme.dto.mypage.ProfileUpdateDto;
import com.example.Plowithme.dto.user.CurrentUserDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "마이페이지")
public class UserController {

    public final UserService userService;

    /**
     회원정보
     */

    @GetMapping("/me")
    @Operation(summary = "현재 회원 정보 조회")
    public ResponseEntity<CommonResponse> getCurrentUser(@CurrentUser User currentUser) {
        CurrentUserDto currentUserDto = userService.getCurrentUser(currentUser);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "현재 회원 정보 조회", currentUserDto);
        log.info("현재 유저 정보 조회 완료:" + currentUserDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/users/{id}")
    @Operation(summary = "회원 계정 설정 조회")
    public ResponseEntity<CommonResponse> find(@Valid @PathVariable("id") Long id, @CurrentUser User currentUser) {
        AccountInfoFindDto accountInfoFindDto = userService.findUser(id, currentUser);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "회원 계정 설정 조회 완료", accountInfoFindDto);
        log.info("회원 계정 설정 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/users/{id}")
    @Operation(summary = "회원 계정 설정 수정")
    public ResponseEntity<CommonResponse> update(@Valid @RequestBody AccountInfoUpdateDto accountInfoUpdateDto, @PathVariable("id") Long id, @CurrentUser User currentUser) {
        userService.updateUser(id, currentUser, accountInfoUpdateDto);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "회원 계정 설정 수정 완료");
        log.info("회원 계정 설정 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     모임 관련
     */

    @GetMapping("/users/{id}/class-count")
    @Operation(summary = "모임 횟수 조회")
    public ResponseEntity<CommonResponse> findClassCount(@PathVariable("id") Long id) {

        int class_count = userService.classCount(id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 횟수 조회 완료", class_count);
        log.info("모임 횟수 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/users/{id}/class-distance")
    @Operation(summary = "모임 총 거리 조회")
    public ResponseEntity<CommonResponse> findClassDistance(@PathVariable("id") Long id) {

        Double class_distance = userService.classDistance(id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 총 거리 조회 완료", class_distance);
        log.info("모임 총 거리 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
    프로필
     */

    @GetMapping("/users/{id}/profile")
    @Operation(summary = "프로필 설정 조회")
    public ResponseEntity<CommonResponse> findProfile(@PathVariable("id") Long id, @CurrentUser User currentUser) {

        ProfileFindDto profileFindDto = userService.findProfile(id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "프로필 조회 완료", profileFindDto);
        log.info("프로필 조회 완료");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + currentUser.getProfile() + "\"").body(response);
    }


    @PostMapping("/users/{id}/profile")
    @Operation(summary = "프로필 사진 수정")
    public ResponseEntity<CommonResponse> uploadProfileImage(@PathVariable("id") Long id, MultipartFile file) {

        userService.updateProfileImage(file, id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "프로필 사진 수정 완료");
        log.info("프로필 사진 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/users/{id}/profile")
    @Operation(summary = "프로필 설정 수정")
    public ResponseEntity<CommonResponse> uploadProfileInfo(@Valid @RequestBody ProfileUpdateDto profileUpdateDto, @PathVariable("id") Long id) {

        userService.updateProfileInfo(id, profileUpdateDto);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "프로필 수정 완료");
        log.info("프로필 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     외부에서 쓰일 프로필
     */

    @GetMapping("/class/{classId}/profile")
    @Operation(summary = "모임 참여자 프로필 조회(프로필 사진 + 닉네임)")
    public ResponseEntity<CommonResponse> findClassProfiles(@PathVariable("classId") Long id) {

        List<JoinedClassProfileFindDto> joinedClassProfileFindDtos = userService.findClassProfiles(id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 참여자 프로필 조회 완료", joinedClassProfileFindDtos );
        log.info("모임 참여자 프로필 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
