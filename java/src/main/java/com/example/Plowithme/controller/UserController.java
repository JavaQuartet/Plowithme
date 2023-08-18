package com.example.Plowithme.controller;


import com.example.Plowithme.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "마이페이지")
public class UserController {
    public final UserService userService;


    final UserService storageService;

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
    //프로필 이미지
    @ResponseBody
    @GetMapping("/mypage/images/{profilename}")
    public Resource downloadImage(@PathVariable String profilename) throws
            MalformedURLException {
        return new UrlResource("프로필:" + profileStore.getFullPath(profilename));
    }


    //프로필 첨부
    @GetMapping("/attach/{userId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long userId) throws MalformedURLException {
        User user = userService.findOne(userId);
        String storeFileName = user.getProfile_image().getStoreFileName();
        String uploadFileName = user.getProfile_image().getUploadFileName();
        UrlResource resource = new UrlResource("profile:" + profileStore.getFullPath(storeFileName));

        log.info("업로드 프로필 명={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }


    //회원 삭제
    @GetMapping("/list/{userId}/delete")
    public String deleteUserForm(@PathVariable("userId") Long userId, Model model) {
        userService.deleteUser(userId);
        return "redirect:/users/list";
    }


    //세션 관리
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

        return "세션 출력";
    }

}