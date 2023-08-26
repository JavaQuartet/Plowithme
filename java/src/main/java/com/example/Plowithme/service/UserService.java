package com.example.Plowithme.service;

import com.example.Plowithme.controller.UserController;
import com.example.Plowithme.dto.request.JoinedClassProfileFindDto;
import com.example.Plowithme.dto.request.mypage.*;
import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.Message;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.FileException;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClassRepository classRepository;


    @Transactional
    public CurrentUserDto getCurrentUser(User currentUser) {
        return new CurrentUserDto(currentUser.getId(), currentUser.getEmail(), currentUser.getName());

    }

    //회원 계정 설정 조회
    @Transactional
    public AccountInfoFindDto findUser(Long id, User currentUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        if (!user.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        AccountInfoFindDto accountInfoFindDto = AccountInfoFindDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getRegion().getAddress())
                .build();

        return accountInfoFindDto;
    }


    //회원 계정 설정 수정
    @Transactional
    public void updateUser(Long id, User currentUser, AccountInfoUpdateDto accountInfoUpdateDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));
        if (!user.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        if (accountInfoUpdateDto.getPassword() != null) {
            accountInfoUpdateDto.setPassword(passwordEncoder.encode(accountInfoUpdateDto.getPassword()));
            user.updatePassword(accountInfoUpdateDto.getPassword());
        }

        if (accountInfoUpdateDto.
                getRegion().getAddress() != null
                || accountInfoUpdateDto.getRegion().getDepth_3() != null
                || accountInfoUpdateDto.getRegion().getDepth_2() != null
                || accountInfoUpdateDto.getRegion().getDepth_1() != null
        ) {
            user.setRegion(accountInfoUpdateDto.getRegion());
        }

    }

    //모임 총 횟수 조회
    @Transactional
    public int classCount(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        return user.getClass_count();
    }

    //모임 총 거리 조회
    @Transactional
    public double classDistance(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        return Double.parseDouble(String.format("%.2f", user.getClass_distance()));
    }

    //프로필 설정 조회

    private final Path root = Paths.get("uploads/profiles");

    @Transactional
    public void updateProfileInfo(Long id, ProfileUpdateDto profileUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));


        if (profileUpdateDto.getNickname() != null) {
            user.setNickname(profileUpdateDto.getNickname());
            user.setIntroduction(profileUpdateDto.getIntroduction());
        }

        if (profileUpdateDto.getIntroduction() != null) {
            user.setIntroduction(profileUpdateDto.getIntroduction());
        }

    }

    @Transactional
    public void updateProfileImage(MultipartFile file, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));


        if (file.isEmpty()) {
            throw new FileException("파일이 없습니다.");
        }

        //확장자 제한
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("png")) {
            throw new FileException("업로드가 불가능한 확장자입니다.");
        }

        String profileName = UUID.randomUUID() + file.getOriginalFilename();
        try {

            if (!user.getProfile().equals("default-image.png")) { //기본 이미지
                //원래 사진 삭제
                Path files = root.resolve(user.getProfile());
                Files.deleteIfExists(files);
            }
            //프로필 사진 추가
            Files.copy(file.getInputStream(), this.root.resolve(profileName));

            user.setProfile(profileName);

        } catch (Exception e) {
            throw new FileException("파일을 수정할 수 없습니다.");
        }

    }

    //프로필 설정 조회
    @Transactional
    public ProfileFindDto findProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        try {
            ProfileFindDto profileFindDto = ProfileFindDto.builder()
                    .profile_url(Paths.get("uploads/profiles").resolve(user.getProfile()).toUri().toURL().toString())
                    .nickname(user.getNickname())
                    .introduction(user.getIntroduction())
                    .build();
            return profileFindDto;

        } catch (Exception e) {
            throw new FileException("파일을 조회할 수 없습니다.");
        }
    }


    //참여 모임 프로필 조회
    @Transactional
    public List<JoinedClassProfileFindDto> findClassProfiles(Long id) {
        ClassEntity classEntity = classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("모임을 찾을 수 없습니다."));

        List<ClassParticipantsEntity> joinList = classEntity.getClassParticipantsEntityList();
        List<JoinedClassProfileFindDto> joinedClassProfileFindDtos = new ArrayList<>();
        for (ClassParticipantsEntity classParticipantsEntity : joinList) {
            User user = userRepository.findById(classParticipantsEntity.getUserid()).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

            joinedClassProfileFindDtos.add(JoinedClassProfileFindDto.toDto(user));
        }
        return joinedClassProfileFindDtos;
    }


}
