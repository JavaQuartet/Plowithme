package com.example.Plowithme.service;

import com.example.Plowithme.dto.UserSummary;
import com.example.Plowithme.dto.request.mypage.AccountInfoFindDto;
import com.example.Plowithme.dto.request.mypage.AccountInfoUpdateDto;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.UserNotFoundException;
import com.example.Plowithme.repository.UserRepository;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //현재 유저 조회
//    @GetMapping("/me")
//    public ResponseEntity<UserSummary> getCurrentUser(UserPrincipal currentUser) {
//        UserSummary userSummary = userService.getCurrentUser(currentUser);
//
//        return new ResponseEntity< >(userSummary, HttpStatus.OK);
//    }
    public UserSummary getCurrentUser(User currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getEmail(),currentUser.getName());

    }
    //회원 계정 설정 조회
    @Transactional
    public AccountInfoFindDto findUser(Long id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        AccountInfoFindDto accountInfoFindDto = AccountInfoFindDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getRegion().getAddress())
                .build();

        return accountInfoFindDto;
    }


    //회원 계정 설정 수정
    @Transactional
    public void updateUser(Long id, AccountInfoUpdateDto accountInfoUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!accountInfoUpdateDto.getName().isEmpty()) {
            user.setName(accountInfoUpdateDto.getName());
        }
        if (!accountInfoUpdateDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(accountInfoUpdateDto.getPassword()));
        }
        if (!accountInfoUpdateDto.getRegion().getAddress().isEmpty()) {
            user.setRegion(accountInfoUpdateDto.getRegion());
        }
    }




//    private final Path root = Paths.get("uploads");
//
//    //프로필 업로드 초기 설정
//    public void init() {
//        try {
//            Files.createDirectories(root);
//        } catch (IOException e) {
//            throw new RuntimeException("파일 업로드 폴더 생성 안됨");
//        }
//    }
//
//    //프로필 저장
//    public void save(MultipartFile file) {
//        try {
//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
//            UUID gi =
//            UserRepository.findById()
//        } catch (Exception e) {
//            if (e instanceof FileAlreadyExistsException) {
//                throw new RuntimeException("파일 이름 이미 존재");
//            }
//
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//  //프로필 조화
//    public Resource load(String filename) {
//        try {
//            Path file = root.resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                throw new RuntimeException("파일을 읽음 불가");
//            }
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("파일 다운로드 불가");
//        }
//    }




}
