package com.example.Plowithme.entity;

import com.example.Plowithme.dto.ClassDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column
    private String maker_id; // 만든사람의 아이디

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClassFileEntity> classFileEntityList = new ArrayList<>();



    public static ClassEntity toSaveEntity(ClassDTO classDTO, String user_id){

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
        classEntity.setMaker_id(user_id);
        return classEntity;
    }


    public static ClassEntity toSaveFileEntity(ClassDTO classDTO, String user_id){
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
        classEntity.setMaker_id(user_id);
        return classEntity;
    }



    //모임 수정
    public static ClassEntity toUpdateEntity(ClassDTO classDTO, String user_id){
        ClassEntity classEntity = new ClassEntity();

        classEntity.setId(classDTO.getId());
        classEntity.setTitle(classDTO.getTitle());

        classEntity.setMember(classDTO.getMember());
        classEntity.setStatus(classDTO.getStatus());

        classEntity.setStart_region(classDTO.getStart_region());
        classEntity.setEnd_region(classDTO.getEnd_region());

        classEntity.setStart_date(classDTO.getStart_date());
        classEntity.setStart_date(classDTO.getEnd_date());

        classEntity.setDescription(classDTO.getDescription());
        /*classEntity.setNotice(classDTO.getNotice());*/
        classEntity.setFileAttached(classDTO.getFileAttached()); // 파일 있음.
        classEntity.setClassjoined(classDTO.getClassjoined()); // 참여함
        classEntity.setMaker_id(user_id);
        return classEntity;
    }
}
