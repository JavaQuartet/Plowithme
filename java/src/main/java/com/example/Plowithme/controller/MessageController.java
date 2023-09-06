package com.example.Plowithme.controller;

import com.example.Plowithme.dto.mypage.MessageFindDto;
import com.example.Plowithme.dto.mypage.MessageSandDto;
import com.example.Plowithme.dto.CommonResponse;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.security.CurrentUser;
import com.example.Plowithme.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "쪽지")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages")
    @Operation(summary = "쪽지 전송")
    public ResponseEntity<CommonResponse> sendMessage(@Valid @RequestBody MessageSandDto messageSandDto, @CurrentUser User currentUser ) {
        messageService.writeMessage(currentUser.getId(),messageSandDto);

        CommonResponse response = new CommonResponse(HttpStatus.CREATED.value(), "쪽지 전송 완료");
        log.info("쪽지 전송 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/messages/{messageId}")
    @Operation(summary = "쪽지 상세 조회")
    public ResponseEntity<CommonResponse> findReceivedMessage(@PathVariable("messageId") Long id) {
        MessageFindDto messageFindDto = messageService.findMessage(id);

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "받은 쪽지 상세 조회 완료", messageFindDto);
        log.info("받은 쪽지 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/messages/received")
    @Operation(summary = "받은 쪽지 조회")
    public ResponseEntity<CommonResponse> findReceivedMessage(@CurrentUser User currentUser) {
        List<MessageFindDto> messageFindDtos = messageService.receivedMessage(currentUser.getId());

        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "받은 쪽지 조회 완료", messageFindDtos );
        log.info("받은 쪽지 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/messages/received/{messageId}")
    @Operation(summary = "받은 쪽지 삭제")
    public  ResponseEntity<CommonResponse> deleteReceivedMessage(@PathVariable("messageId") Long id, @CurrentUser User currentUser) {


        messageService.deleteMessageByReceiver(id, currentUser);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "받은 쪽지 삭제" );
        log.info("받은 쪽지 삭제");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/messages/sent")
    @Operation(summary = "보낸 쪽지 조회")
    public ResponseEntity<CommonResponse> findSentMessage(@CurrentUser User currentUser) {

        List<MessageFindDto> messageFindDtos = messageService.sentMessage(currentUser);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "보낸 쪽지 조회", messageFindDtos );
        log.info("보낸 쪽지 조회");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/messages/sent/{messageId}")
    @Operation(summary = "보낸 쪽지 삭제")
    public ResponseEntity<CommonResponse> deleteSentMessage(@PathVariable("messageId") Long id, @CurrentUser User currentUser) {

        Object message = messageService.deleteMessageBySender(id, currentUser);
        CommonResponse response = new CommonResponse(HttpStatus.OK.value(), "보낸 쪽지 삭제", message );
        log.info("보낸 쪽지 삭제");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}