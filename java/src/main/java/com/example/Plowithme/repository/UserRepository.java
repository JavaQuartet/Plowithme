package com.example.Plowithme.repository;
import com.example.Plowithme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //등록
    User save(User user);

    //단건 조회
    Optional<User> findById(Long id);
    //리스트 조회
    List<User> findAll();
    //이메일 조회
    Optional<User> findByEmail(String Email);

    //삭제
    void delete(User user);


}
