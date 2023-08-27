package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.Message;
import com.example.Plowithme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);

    Optional<Message> findById(Long id);

}
