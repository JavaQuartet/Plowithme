package com.example.Plowithme.controller;

import com.example.Plowithme.dto.request.mypage.MessageFindDto;
import com.example.Plowithme.dto.request.mypage.MessageSandDto;
import com.example.Plowithme.dto.response.CommonResponse;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.UserRepository;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://43.200.172.177:8080, http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "쪽지")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    @Operation(summary = "쪽지 전송")
    @PostMapping("/messages")
    public ResponseEntity<CommonResponse> sendMessage(@Valid @RequestBody MessageSandDto messageSandDto, @CurrentUser User currentUser ) {
        messageService.writeMessage(currentUser.getId(),messageSandDto);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "쪽지 전송 완료");
        log.info("쪽지 전송 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "쪽지 상세 조회")
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<CommonResponse> findReceivedMessage(@PathVariable("messageId") Long id) {
        MessageFindDto messageFindDto = messageService.findMessage(id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "받은 쪽지 상세 조회 완료", messageFindDto);
        log.info("받은 쪽지 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




    @Operation(summary = "받은 쪽지 조회")
    @GetMapping("/messages/received")
    public ResponseEntity<CommonResponse> findReceivedMessage(@CurrentUser User currentUser) {
        List<MessageFindDto> messageFindDtos = messageService.receivedMessage(currentUser.getId());

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "받은 쪽지 조회 완료", messageFindDtos );
        log.info("받은 쪽지 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "받은 쪽지 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/received/{messageId}")
    public  ResponseEntity<CommonResponse> deleteReceivedMessage(@PathVariable("messageId") Long id, @CurrentUser User currentUser) {


        messageService.deleteMessageByReceiver(id, currentUser);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "받은 쪽지 삭제" );
        log.info("받은 쪽지 삭제");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }




    @Operation(summary = "보낸 쪽지 조회")
    @GetMapping("/messages/sent")
    public ResponseEntity<CommonResponse> findSentMessage(@CurrentUser User currentUser) {

        List<MessageFindDto> messageFindDtos = messageService.sentMessage(currentUser);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "보낸 쪽지 조회", messageFindDtos );
        log.info("보낸 쪽지 조회");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(summary = "보낸 쪽지 삭제")
    @DeleteMapping("/messages/sent/{messageId}")
    public ResponseEntity<CommonResponse> deleteSentMessage(@PathVariable("messageId") Long id, @CurrentUser User currentUser) {

        Object message = messageService.deleteMessageBySender(id, currentUser);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "보낸 쪽지 삭제", message );
        log.info("보낸 쪽지 삭제");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}