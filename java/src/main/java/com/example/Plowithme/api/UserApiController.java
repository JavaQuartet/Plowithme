package com.example.Plowithme.api;

import com.example.Plowithme.dto.JoinDto;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserApiController {



//    //회원 조회
//    public Result membersV2() {
//
//        List<Member> findMembers = userService.findMembers();
//        //엔티티 -> DTO 변환
//        List<MemberDto> collect = findMembers.stream()
//                .map(m -> new MemberDto(m.getName()))
//                .collect(Collectors.toList());
//
//        return new Result(collect);
//    }
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private T data;
//    }



}