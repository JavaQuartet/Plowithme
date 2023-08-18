package com.example.Plowithme.controller;


import com.example.Plowithme.dto.request.RegisterDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
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

    //회원 계정 설정 조회
    @GetMapping("/users/{id}")
    @Operation(summary = "회원 계정 설정 조회")
    public ResponseEntity<CommonResponse> update (@Valid @@PathVariable("id") Long id, @RequestBody RegisterDto registerDto) {
        userService.updateUser(id, registerDto);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(),"회원 계정 설정 조회 완료");
        log.info("회원 계정 설정 조회 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
