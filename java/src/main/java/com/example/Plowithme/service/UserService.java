package com.example.Plowithme.service;

import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        validateDuplicateUser(user); //중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }

    //중복 회원 확인
    private void validateDuplicateUser(User user) {
        Optional<User> findUsers = userRepository.findByEmail(user.getEmail());
        if (findUsers.isPresent()) {throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 조회
    public List<User> findUsers() { // 전체
        return userRepository.findAll();
    }
    public User findOne(Long Id) { // 단건
        return userRepository.findOne(Id);
    }

    //회원 계정 수정
    @Transactional
    public void editUserAccount(Long id,String name, String password, String region)
    {
        User user = userRepository.findOne(id);

        user.setPassword(password);
        user.setName(name);
        user.setRegion(region);
    }

//    //회원 프로필 수정
//    @Transactional
//    public void editUserProfile(Long id,String nickname, Profile profile_image)
//    {
//        User user = userRepository.findOne(id);
//
//        user.setNickname(nickname);
//        user.setProfile_image(profile_image);
//
//    }

    //회원 삭제
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findOne(id);
        userRepository.delete(user);
    }
}
