package com.example.Plowithme.service;

import com.example.Plowithme.dto.request.mypage.MessageFindDto;
import com.example.Plowithme.dto.request.mypage.MessageSandDto;
import com.example.Plowithme.entity.Message;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.MessageRepository;
import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    //쪽지 쓰기
    @Transactional
    public void writeMessage(Long id, MessageSandDto messageSandDto) {
        User sender = userRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });
        User receiver = userRepository.findById(messageSandDto.getReceiverId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });
        Message message = Message.builder()
                .receiver(receiver)
                .sender(sender)
                .content(messageSandDto.getContent())
                .deletedByReceiver(false)
                .deletedBySender(false)
                .build();

        messageRepository.save(message);

    }
    //쪽지 상세 조회
    @Transactional(readOnly = true)
    public MessageFindDto findMessage(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("쪽지를 찾을 수 없습니다.");
        });


        MessageFindDto messageFindDto = MessageFindDto.toDto(message);

        return messageFindDto;
    }



    //받은 쪽지 조회

    @Transactional(readOnly = true)
    public List<MessageFindDto> receivedMessage(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });


        List<Message> messages = messageRepository.findAllByReceiver(user);
        List<MessageFindDto> messageFindDtos = new ArrayList<>();

        for(Message message : messages) {
            if(!message.isDeletedByReceiver()) {
                messageFindDtos.add(MessageFindDto.toDto(message));
            }
        }
        return messageFindDtos;
    }




    // 받은 쪽지 삭제
    @Transactional
    public Object deleteMessageByReceiver(Long id, User currentUser) {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });

        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("메시지를 찾을 수 없습니다.");
        });


        if(user == message.getSender()) {
            message.deleteByReceiver();
            if (message.isDeleted()) {
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }


    //보낸 쪽지 조회
    @Transactional(readOnly = true)
    public List<MessageFindDto> sentMessage(User user) {
        // 한 유저가 받은 모든 메시지
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageFindDto> messageFindDtos = new ArrayList<>();

        for(Message message : messages) {
            if(!message.isDeletedBySender()) {
                messageFindDtos.add(MessageFindDto.toDto(message));
            }
        }
        return messageFindDtos;
    }


    // 보낸 편지 삭제
    @Transactional
    public Object deleteMessageBySender(Long id, User currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });

        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("메시지를 찾을 수 없습니다.");
        });


        if(user == message.getSender()) {
            message.deleteBySender(); // 받은 사람에서만 삭제
            if (message.isDeleted()) {
                // 전체 삭제
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }


    }
}