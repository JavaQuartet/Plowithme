package com.example.Plowithme.Service;


import com.example.Plowithme.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final userRepository userRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(User user) {

        validateDuplicateuser(user); //중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateuser(user user) {
        List<user> findusers = userRepository.findByName(user.getName());
        if (!findusers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<user> findusers() {
        return userRepository.findAll();
    }

    public user findOne(Long userId) {
        return userRepository.findOne(userId);
    }

}
