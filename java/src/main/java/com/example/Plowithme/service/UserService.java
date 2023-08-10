package com.example.Plowithme.service;

import com.example.Plowithme.entity.Profile;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //로그인
    public User login(String email, String password) {
        return userRepository.findByEmail(email).filter(m -> m.getPassword().equals(password)).orElse(null);
    }

    //회원 가입
    @Transactional
    public Long join(User user) {
        //중복 검증
        Optional<User> findUsers = userRepository.findByEmail(user.getEmail());
        if (findUsers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");}
        //중복되지 않는다면
        userRepository.save(user);
        return user.getId();

    }
//    //회원 조회
//    public List<User> findUsers() {
//        return userRepository.findAll();
//    }
//    public User findOne(Long Id) { // 단건
//        return userRepository.findById(Id).orElse(null);
//    }
//
//    //회원 계정 수정
//    @Transactional
//    public void editUserAccount(Long id,String name, String password, String region)
//    {
//        User user = userRepository.findById(id).orElse(null);
//
//        user.setPassword(password);
//        user.setName(name);
//        user.setRegion(region);
//    }

//    // 프로필 등록
//    @Value("${file.dir}") //왜 안되는겨 ㅠㅠㅜ
//    private String fileDir;
//    public Profile storeFile(MultipartFile multipartFile) throws IOException
//    {
//        if (multipartFile.isEmpty()) {
//            return null;
//        }
//        String originalFilename = multipartFile.getOriginalFilename();
//        String uuid = UUID.randomUUID().toString();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String storeFileName = uuid + extension;
//        String storeFilePath = fileDir + storeFileName;
//
//        multipartFile.transferTo(new File(storeFilePath));
//        return new Profile(originalFilename, storeFileName);
//    }
//
//
//    //프로필 조회
//    @Transactional
//    public Profile showProfile(Long id){
//        User user = userRepository.findById(id);
//        return user.getProfile_image();
//
//    }


//    //회원 삭제
//    @Transactional
//    public void deleteUser(Long id) {
//
//        User user = userRepository.findById(id).orElse(null);
//        userRepository.delete(user);
//    }

}
