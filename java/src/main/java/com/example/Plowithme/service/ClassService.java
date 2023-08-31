package com.example.Plowithme.service;


import com.example.Plowithme.dto.request.meeting.ClassSaveDto;
import com.example.Plowithme.dto.request.meeting.ClassFindDto;
import com.example.Plowithme.dto.request.meeting.ClassUpdateDto;
import com.example.Plowithme.dto.request.mypage.MessageFindDto;
import com.example.Plowithme.entity.*;
import com.example.Plowithme.dto.request.meeting.ClassDTO;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.ClassFileRepository;
import com.example.Plowithme.repository.ClassParticipantRepository;
import com.example.Plowithme.repository.ClassRepository;
import com.example.Plowithme.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final ClassFileRepository classFileRepository;
    private final ClassParticipantRepository classParticipantRepository;
    private final UserRepository userRepository;

//    public ClassEntity save(ClassDTO classDTO, User user_id) throws IOException {
///*        if(classDTO.getClassFile().isEmpty()){*/
//            ClassEntity classEntity = ClassEntity.toSaveEntity(classDTO, user_id);
//            classRepository.save(classEntity);
//            return classEntity;
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
//    }

    public ClassEntity saveClass(ClassSaveDto classSaveDto, Long id) {
        /*LocalDate now = LocalDate.now();*/
        Optional<User> user = userRepository.findById(id);

        ClassEntity classEntity = ClassEntity.builder()
                .title(classSaveDto.getTitle())
                .member_max(classSaveDto.getMember_max())
                .member_current(1)
                .status(1)
                .start_region(classSaveDto.getStart_region())
                .end_region(classSaveDto.getEnd_region())
                .description(classSaveDto.getDescription())
                .start_date(classSaveDto.getStart_date())
                .start_year(classSaveDto.getStart_year())
                .start_month(classSaveDto.getStart_month())
                .start_day(classSaveDto.getStart_day())
                .end_date(classSaveDto.getEnd_date())
                /*.current_day(now.getDayOfYear())*/
                .maker_id(id)
                .maker_nickname(user.get().getNickname())
                .maker_profile(user.get().getProfileUrl(user.get().getProfile()))
                .distance((double) 0)
                .build();
        return classEntity;


    }


    public List<ClassDTO> findAll() {
        List<ClassEntity> classEntityList = classRepository.findAll();
        List<ClassDTO> classDTOList = new ArrayList<>();
        for (ClassEntity classEntity : classEntityList) {
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


    public void participant(ClassEntity classEntity, User user) {
        ClassParticipantsEntity classParticipantsEntity = ClassParticipantsEntity.toSaveParticiantEntity(classEntity, user);
        classParticipantRepository.save(classParticipantsEntity);
    }

    @Transactional
    public void deleteparticipant(ClassEntity classEntity, User user) {// 모임 참여 취소 할때 사용
        classParticipantRepository.deleteClassParticipantsEntityByUseridAndMeetingid(user.getId(), classEntity.getId());
    }

    /*    public void deleteAllParticipant(){

        }*/
    @Transactional //모임 삭제
    public void delete(ClassEntity classEntity) {
        classParticipantRepository.deleteClassParticipantsEntitiesByClassEntity(classEntity);
        classRepository.deleteById(classEntity.getId());
    }


    //모임 수정
/*    @Transactional
    public ClassDTO update(ClassEntity Class, User user_id) {
        ClassEntity classEntity = ClassEntity.toUpdateEntity(Class, user_id);
        classRepository.save(classEntity);
        return findById(Class.getId());
    }*/

/*    public Long patchUpdate(Long id, ClassDTO classDTO) throws SQLException {

        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        List<ClassDTO> list = new ArrayList<>();
        list.add(classDTO);


        for (int i=0;i< list.size();i++) {
            if (list.get(i).getDistance() != null) {
                classEntity.setDistance(list.get(i).getDistance());
            }
        }

        classRepository.save(classEntity);

        return id;
    }*/





    //    @Transactional
//    public List<ClassSearchDto> searchClass(String keyword) {
//        List<ClassEntity> classEntities = classRepository.findAllSearch(keyword).stream()
//                .map(ClassSearchDto::new)
//                .collect(Collectors.toList());
//
//
//        return
//    }
    //모임 검색
    @Transactional
    public List<ClassFindDto> searchStart_region(String keyword, Pageable pageable) {


        List<ClassEntity> classEntities = classRepository.findByTitleContaining(keyword, pageable).stream().toList();
        List<ClassFindDto> classFindDtos = new ArrayList<>();

        for (ClassEntity classEntity : classEntities) {
            if (classEntity.getStatus() == 1) // 모집중인 모임
                classFindDtos.add(ClassFindDto.toDto(classEntity));
        }
        return classFindDtos;
    }


    @Transactional
    public ClassDTO updated(Long id, ClassUpdateDto classDTO) {
        ClassEntity entity = classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        /*User user = userRepository.findById(user_id.getId());*/

        if (classDTO.getTitle() != null) {
            entity.setTitle(classDTO.getTitle());
        }
        if (classDTO.getStart_region() != null) {
            entity.setStart_region(classDTO.getStart_region());
        }
        if (classDTO.getMember_max() != null) {
            entity.setMember_max(classDTO.getMember_max());
        }
        if (classDTO.getEnd_region() != null) {
            entity.setEnd_region(classDTO.getEnd_region());
        }
        if (classDTO.getDescription() != null) {
            entity.setDescription(classDTO.getDescription());
        }
        if (classDTO.getStart_date() != null) {
            entity.setStart_date(classDTO.getStart_date());
        }
        if (classDTO.getEnd_date() != null) {
            entity.setEnd_date(classDTO.getEnd_date());
        }
        if (classDTO.getDistance() != null) {
            entity.setDistance(classDTO.getDistance());
        }
        if (classDTO.getStart_year() != null) {
            entity.setStart_year(classDTO.getStart_year());
        }
        if (classDTO.getStart_month() != null) {
            entity.setStart_month(classDTO.getStart_month());
        }
        if (classDTO.getStart_day() != null) {
            entity.setStart_day(classDTO.getStart_day());
        }
        if (classDTO.getNotice() != null){
            entity.setNotice(classDTO.getNotice());
        }

/*        for (ClassParticipantsEntity user : classDTO.getClassParticipantsEntityList()) {
            Optional<User> optionalUser = userRepository.findById(user.getUserid());
            optionalUser.get().setClass_count(optionalUser.get().getClass_count() + 1);
            optionalUser.get().setClass_distance(optionalUser.get().getClass_distance() + classDTO.getDistance());
            userRepository.save(optionalUser.get());
        }*/

        ClassDTO classDTO1 = findById(id);

        return classDTO1;
    }

    @Transactional
    public void end_class(ClassDTO classDTO) {
        for (ClassParticipantsEntity user : classDTO.getClassParticipantsEntityList()) {
            Optional<User> optionalUser = userRepository.findById(user.getUserid());
            optionalUser.get().setClass_count(optionalUser.get().getClass_count() + 1);
            optionalUser.get().setClass_distance(optionalUser.get().getClass_distance() + classDTO.getDistance());
            userRepository.save(optionalUser.get());
        }
    }
}
