package com.example.Plowithme.controller;


import com.example.Plowithme.dto.request.mypage.CurrentUserDto;
import com.example.Plowithme.dto.request.mypage.AccountInfoFindDto;
import com.example.Plowithme.dto.request.mypage.AccountInfoUpdateDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor

@Tag(name = "마이페이지")
public class UserController {
    public final UserService userService;



    //연결 test
    @GetMapping("/test")
    public ResponseEntity<?> Test(){
        return ResponseEntity.ok("test");
    }


    //현재 유저 정보 조회
    @GetMapping("/me")
    public ResponseEntity<CommonResponse> getCurrentUser(@CurrentUser User currentUser) {

        CurrentUserDto currentUserDto = userService.getCurrentUser(currentUser);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"현재 회원 정보 조회", currentUserDto);
        log.info("현재 유저 정보 조회 완료:" + currentUserDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //회원 계정 설정 조회
    @GetMapping("/users/{id}")
    @Operation(summary = "회원 계정 설정 조회")
    public ResponseEntity<CommonResponse> find (@Valid @PathVariable("id") Long id, @CurrentUser User currentUser) {
        AccountInfoFindDto accountInfoFindDto = userService.findUser(id, currentUser);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"회원 계정 설정 조회 완료", accountInfoFindDto);
        log.info("회원 계정 설정 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //회원 계정 설정 수정 - 차후 수정 예정
   // @PatchMapping("/users/{id}")
    @Operation(summary = "회원 계정 설정 수정")
    public ResponseEntity<CommonResponse> find (@Valid @PathVariable("id") Long id, @CurrentUser User currentUser, @RequestBody AccountInfoUpdateDto accountInfoUpdateDto) {
        userService.updateUser(id, currentUser, accountInfoUpdateDto);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(),"회원 계정 설정 수정 완료");
        log.info("회원 계정 설정 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }





//    public final UserService storageService;

//    //프로필 수정(업로드)
//    @PostMapping("/upload")
//    public ResponseEntity<Response> uploadFile(@RequestParam("file") MultipartFile file) {
//        String message = "";
//        try {
//            storageService.save(file);
//
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new Response(message));
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(message));
//        }
//    }
//
//    //프로필 조회
//    @GetMapping("/files/{filename}")
//    @ResponseBody
//    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//        Resource file = storageService.load(filename);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

}
