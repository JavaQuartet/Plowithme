package com.example.Plowithme.service;

import com.example.Plowithme.dto.request.mypage.CurrentUserDto;
import com.example.Plowithme.dto.request.mypage.AccountInfoFindDto;
import com.example.Plowithme.dto.request.mypage.AccountInfoUpdateDto;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CurrentUserDto getCurrentUser(User currentUser) {
        return new CurrentUserDto(currentUser.getId(), currentUser.getEmail(),currentUser.getName());

    }
    //회원 계정 설정 조회
    @Transactional
    public AccountInfoFindDto findUser(Long id, User currentUser){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        if(!user.getId().equals(currentUser.getId())){
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
        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        if (accountInfoUpdateDto.getName()!=null) {
            user.setName(accountInfoUpdateDto.getName());
        }
        if (accountInfoUpdateDto.getPassword()!=null) {
            user.setPassword(passwordEncoder.encode(accountInfoUpdateDto.getPassword()));
        }
        if (accountInfoUpdateDto.
                getRegion().getAddress()!=null
                || accountInfoUpdateDto.getRegion().getDepth_3()!=null
                || accountInfoUpdateDto.getRegion().getDepth_2()!=null
                || accountInfoUpdateDto.getRegion().getDepth_1()!=null
        ) {
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
