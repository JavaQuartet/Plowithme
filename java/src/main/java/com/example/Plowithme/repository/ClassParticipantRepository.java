package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ClassParticipantRepository extends JpaRepository<ClassParticipantsEntity, Long> {
    /*Optional<ClassParticipantsEntity> findByUser_id(Long user_id);*/
    /*Optional<ClassParticipantsEntity> deleteAllByClassId(Long user_id);*/

}
