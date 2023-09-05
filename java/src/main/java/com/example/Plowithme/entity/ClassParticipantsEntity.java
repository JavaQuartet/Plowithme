package com.example.Plowithme.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.Profile;

import javax.swing.*;

@Entity
@Getter
@Setter
@Table(name = "class_participant_table")
public class ClassParticipantsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participant_id;

    @Column
    private Long userid; //가입한 유저 id

    @Column
    private Long meetingid; //가입한 모임 id

    @Column
    private String profile_image; //프로필 url

    @Column
    private String user_nickname; //닉네임

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private ClassEntity classEntity;



    public static ClassParticipantsEntity toSaveParticiantEntity(ClassEntity classEntity, User user){
        ClassParticipantsEntity classParticipantsEntity = new ClassParticipantsEntity();

        classParticipantsEntity.setUserid(user.getId());
        classParticipantsEntity.setMeetingid(classEntity.getId());

        classParticipantsEntity.setProfile_image(user.getProfile());
        classParticipantsEntity.setUser_nickname(user.getNickname());

        classParticipantsEntity.setClassEntity(classEntity);

        return classParticipantsEntity;
    }
}