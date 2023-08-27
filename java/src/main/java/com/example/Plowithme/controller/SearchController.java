package com.example.Plowithme.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

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
