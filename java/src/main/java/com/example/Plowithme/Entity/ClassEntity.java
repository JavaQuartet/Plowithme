package com.example.Plowithme.Entity;

import com.example.Plowithme.dto.ClassDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "class_table")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String title;

    @Column
    private int member;

    @Column
    private int status;

    @Column
    private String start_region;

    @Column
    private String end_region;

    @Column
    private String description;


    @Column
    private String notice;


    public static ClassEntity toSaveEntity(ClassDTO classDTO){
        ClassEntity classEntity = new ClassEntity();
        classEntity.setTitle(classDTO.getTitle());
        classEntity.setMember(classDTO.getMember());
        classEntity.setStatus(1);
        classEntity.setStart_region(classDTO.getStart_region());
        classEntity.setEnd_region(classDTO.getEnd_region());
        classEntity.setDescription(classDTO.getDescription());

        classEntity.setNotice(classEntity.getNotice());
        return classEntity;
    }

    @CreationTimestamp
    @Column()
    private LocalDateTime start_date;

    @CreationTimestamp
    @Column()
    private LocalDateTime end_date;

    //모임 수정
    public static ClassEntity toUpdateEntity(ClassDTO classDTO){
        ClassEntity classEntity = new ClassEntity();

        classEntity.setId(classDTO.getId());
        classEntity.setTitle(classDTO.getTitle());
        classEntity.setMember(classDTO.getMember());
        classEntity.setStatus(classDTO.getStatus());
        classEntity.setStart_region(classDTO.getStart_region());
        classEntity.setEnd_region(classDTO.getEnd_region());
        classEntity.setDescription(classDTO.getDescription());

        classEntity.setNotice(classEntity.getNotice());
        return classEntity;
    }

}
