package com.example.Plowithme.Service;

import com.example.Plowithme.Dto.UserForm;
import com.example.Plowithme.Entity.User;
import com.example.Plowithme.Repository.UserRepository;
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
        if (findUsers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 조회
    public List<User> findUsers() { // 전체
        return userRepository.findAll();
    }

    public User findOne(Long userId) { //단건
        return userRepository.findOne(userId);
    }

//
//    //회원 삭제
//    public void deleteById(Long id) {userRepository.delete(id);}
}
