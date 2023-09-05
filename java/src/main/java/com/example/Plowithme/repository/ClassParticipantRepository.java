package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.ClassParticipantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassParticipantRepository extends JpaRepository<ClassParticipantsEntity, Long> {

    Optional<ClassParticipantsEntity> deleteClassParticipantsEntityByUseridAndMeetingid(Long userid, Long meetingid);


    Optional<ClassParticipantsEntity> findClassParticipantsEntityByUseridAndClassEntity(Long userid, ClassEntity classEntity);

    Optional<ClassParticipantsEntity> deleteClassParticipantsEntitiesByClassEntity(ClassEntity classEntity);
}
