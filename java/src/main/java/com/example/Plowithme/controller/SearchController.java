package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.ClassSearchDto;
import com.example.Plowithme.dto.request.JoinedClassProfileFindDto;
import com.example.Plowithme.dto.request.mypage.MessageFindDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.service.ClassService;
import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "모임 검색")
public class SearchController {
//    public final ClassService classService;
//    @GetMapping("/classes/search")
//    public List<ClassSearchDto> ClassSearchDto(@RequestParam(value="keyword") String keyword) {
//
//        List<ClassSearchDto> classSearchDtos = classService.searchClass(keyword);
//
//
//        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "모임 참여자 프로필 조회 완료", classSearchDtos );
//        log.info("모임 참여자 프로필 조회 완료");
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
}
