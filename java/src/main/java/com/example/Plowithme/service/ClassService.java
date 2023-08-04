package com.example.Plowithme.service;

import com.example.Plowithme.Entity.ClassEntity;
import com.example.Plowithme.Entity.ClassFileEntity;
import com.example.Plowithme.dto.ClassDTO;
import com.example.Plowithme.repository.ClassFileRepository;
import com.example.Plowithme.repository.ClassRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void save(ClassDTO classDTO) throws IOException {
        if(classDTO.getClassFile().isEmpty()){
            ClassEntity classEntity = ClassEntity.toSaveEntity(classDTO);
            classRepository.save(classEntity);
        }else{
            MultipartFile classFile = classDTO.getClassFile();
            String originalFilename = classFile.getOriginalFilename();
            String storedFilename = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/plowithme_img/" + storedFilename;
            classFile.transferTo(new File(savePath));

            ClassEntity classEntity = ClassEntity.toSaveFileEntity(classDTO);
            Long savedId = classRepository.save(classEntity).getId();
            ClassEntity Class = classRepository.findById(savedId).get();

            ClassFileEntity classFileEntity = ClassFileEntity.toClassFileEntity(Class, originalFilename, storedFilename);
            classFileRepository.save(classFileEntity);
        }
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


    //모임 수정
    public ClassDTO update(ClassDTO classDTO) {
        ClassEntity classEntity = ClassEntity.toUpdateEntity(classDTO);
        classRepository.save(classEntity);
        return findById(classDTO.getId());
    }

    //모임 삭제
    public void delete(Long id) {
        classRepository.deleteById(id);
        classFileRepository.deleteById(id);
    }
}
