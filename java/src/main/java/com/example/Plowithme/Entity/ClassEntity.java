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
public class ClassEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String title; // 모임 이름

    @Column
    private int member; // 모임 전원

    @Column
    private int status; // 모임 인원

    @Column
    private String start_region; // 시작 지역

    @Column
    private String end_region; // 도착 지역

    @Column
    private String description; // 모임 설명


    @Column
    private String notice; // 공지


    @Column()
    private String start_date; // 시작 시간


    @Column()
    private String end_date; // 도착 시간

    @Column
    private int fileAttached; // 1 or 0

    @Column
    private int classjoined; // 1 or 0


    public static ClassEntity toSaveEntity(ClassDTO classDTO){
        ClassEntity classEntity = new ClassEntity();
        classEntity.setTitle(classDTO.getTitle());
        classEntity.setMember(classDTO.getMember());
        classEntity.setStatus(1);
        classEntity.setStart_region(classDTO.getStart_region());
        classEntity.setEnd_region(classDTO.getEnd_region());

        classEntity.setStart_date(classDTO.getStart_date());
        classEntity.setStart_date(classDTO.getEnd_date());

        classEntity.setDescription(classDTO.getDescription());

        classEntity.setNotice(classEntity.getNotice());
        classEntity.setFileAttached(0); // 파일 없음.
        classEntity.setClassjoined(1); // 참여함
        return classEntity;
    }

    public static ClassEntity toSaveFileEntity(ClassDTO classDTO){
        ClassEntity classEntity = new ClassEntity();
        classEntity.setTitle(classDTO.getTitle());
        classEntity.setMember(classDTO.getMember());
        classEntity.setStatus(1);
        classEntity.setStart_region(classDTO.getStart_region());
        classEntity.setEnd_region(classDTO.getEnd_region());

        classEntity.setStart_date(classDTO.getStart_date());
        classEntity.setStart_date(classDTO.getEnd_date());

        classEntity.setDescription(classDTO.getDescription());

        classEntity.setNotice(classEntity.getNotice());
        classEntity.setFileAttached(1); // 파일 있음.
        classEntity.setClassjoined(1); // 참여함
        
        return classEntity;
    }



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
        classEntity.setStart_date(classDTO.getStart_date());
        classEntity.setStart_date(classDTO.getEnd_date());
        classEntity.setNotice(classEntity.getNotice());
        return classEntity;
    }

}
