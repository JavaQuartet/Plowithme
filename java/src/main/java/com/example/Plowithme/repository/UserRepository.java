package com.example.Plowithme.repository;

import com.example.Plowithme.entity.Message;
import com.example.Plowithme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
//    Boolean existsByUsername(String username);
//    Optional<User> findByName(String name);
//    Boolean existsByEmail(String email);



}


