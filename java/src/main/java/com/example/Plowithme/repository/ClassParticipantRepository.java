package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.ClassParticipantsEntity;
import com.example.Plowithme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassParticipantRepository extends JpaRepository<ClassParticipantsEntity, Long> {
/*    Optional<ClassParticipantsEntity> findByUserid(Long user_id);
    *//*Optional<ClassParticipantsEntity> deleteAllByClassId(Long user_id);*//*
    Optional<ClassParticipantsEntity> findByClassid(Long classid);*/

    Optional<ClassParticipantsEntity> deleteByUseridAndClassid(Long userid, Long classid);


}
