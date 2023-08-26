package com.example.Plowithme.service;


import com.example.Plowithme.entity.*;
import com.example.Plowithme.dto.ClassDTO;
import com.example.Plowithme.repository.ClassFileRepository;
import com.example.Plowithme.repository.ClassParticipantRepository;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.events.Event;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final ClassFileRepository classFileRepository;
    private final ClassParticipantRepository classParticipantRepository;
    private final UserRepository userRepository;

    public ClassEntity save(ClassDTO classDTO, User user_id) throws IOException {
/*        if(classDTO.getClassFile().isEmpty()){*/
            ClassEntity classEntity = ClassEntity.toSaveEntity(classDTO, user_id);
            classRepository.save(classEntity);
            return classEntity;
/*        }else{
            MultipartFile classFile = classDTO.getClassFile();
            String originalFilename = classFile.getOriginalFilename();
            String storedFilename = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/plowithme_img/" + storedFilename;
            classFile.transferTo(new File(savePath));

            ClassEntity classEntity = ClassEntity.toSaveFileEntity(classDTO, user_id);
            Long savedId = classRepository.save(classEntity).getId();
            ClassEntity Class = classRepository.findById(savedId).get();

            ClassFileEntity classFileEntity = ClassFileEntity.toClassFileEntity(Class, originalFilename, storedFilename);
            classFileRepository.save(classFileEntity);
        }*/
    }

    public List<ClassDTO> findAll(){
        List<ClassEntity> classEntityList = classRepository.findAll();
        List<ClassDTO> classDTOList = new ArrayList<>();
        for(ClassEntity classEntity: classEntityList){
            classDTOList.add(ClassDTO.toClassDTO(classEntity));
        }
        return classDTOList;
    }

    @Transactional
    public void updatestatus(Long id) {
        classRepository.updateStatus(id);
    }

    @Transactional
    public void downstatus(Long id) {
        classRepository.downStatus(id);
    }


    public ClassDTO findById(Long id) {
        Optional<ClassEntity> optionalClassEntity = classRepository.findById(id);
        if (optionalClassEntity.isPresent()) {
            ClassEntity classEntity = optionalClassEntity.get();
            ClassDTO classDTO = ClassDTO.toClassDTO(classEntity);
            return classDTO;
        } else {
            return null;
        }
    }



    public void participant(ClassEntity classEntity ,User user) {
        ClassParticipantsEntity classParticipantsEntity = ClassParticipantsEntity.toSaveParticiantEntity(classEntity, user);
        classParticipantRepository.save(classParticipantsEntity);
    }

    @Transactional
    public void deleteparticipant(ClassEntity classEntity, User user) {// 모임 참여 취소 할때 사용
        classParticipantRepository.deleteByUseridAndClassid(user.getId(), classEntity.getId());
    }

/*    public void deleteAllParticipant(){

    }*/
    @Transactional //모임 삭제
    public void delete(Long id) {
        classRepository.deleteById(id);
    }



    //모임 수정
    @Transactional
    public ClassDTO update(ClassEntity Class, User user_id) {
        ClassEntity classEntity = ClassEntity.toUpdateEntity(Class, user_id);
        classRepository.save(classEntity);
        return findById(Class.getId());
    }

    @Transactional
    public void end_class(ClassDTO classDTO){
        for(ClassParticipantsEntity user : classDTO.getClassParticipantsEntityList()){
            Optional<User> optionalUser = userRepository.findById(user.getUserid());
            optionalUser.get().setClass_count(optionalUser.get().getClass_count() + 1);
            optionalUser.get().setClass_distance(optionalUser.get().getClass_distance() + classDTO.getDistance());
            userRepository.save(optionalUser.get());
        }
    }





}
