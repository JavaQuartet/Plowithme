package com.example.Plowithme.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class")
public class ClassEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;

    @Column
    private String title; //모임 이름

    @Column
    private Integer member_max; //모임 정원

    @Column
    private Integer member_current;//모임 인원

    @Column
    private int status; //모임 상태: 활성 = 1 , 종료= 0

    @Column
    private String startRegion; //시작 지역

    @Column
    private String end_region; //도착 지역

    @Column
    private String description; //모임 설명

    @Column
    private String notice; //공지

    @Column
    private String start_date; //시작 시간

    @Column
    private Integer start_year; //시작 년

    @Column
    private Integer start_month; //시작 월

    @Column
    private Integer start_day; //시작 일

    @Column
    private String end_date; //도착 시간

    @Column
    private Double distance; //걸은 거리

    @Column
    private Long maker_id; //만든사람의 아이디

    @Column
    private String maker_nickname; //만든이 닉네임

    @Column
    private String maker_profile; //만든이 프로필

    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY)
    private List<ClassParticipantsEntity> classParticipantsEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY)
    private List<ClassNoticeEntity> classNoticeEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //모임 참여자 삭제
    public void classParticipantsDelete(ClassParticipantsEntity classParticipantsEntity){
        this.getClassParticipantsEntityList().remove(classParticipantsEntity);
    }


}
