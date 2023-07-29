package com.example.Plowithme.service;

import com.example.Plowithme.Entity.ClassEntity;
import com.example.Plowithme.dto.ClassDTO;
import com.example.Plowithme.repository.ClassRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository classRepository;

    public void save(ClassDTO classDTO){
        ClassEntity classEntity = ClassEntity.toSaveEntity(classDTO);
        classRepository.save(classEntity);
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
    /*public void updatestatus(Long id) {
        classRepository.updateHits(id);
    }*/
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
}
