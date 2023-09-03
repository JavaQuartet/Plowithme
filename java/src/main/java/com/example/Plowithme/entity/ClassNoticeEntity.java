package com.example.Plowithme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Getter
@Setter
@Table(name = "class_notice_table")
public class ClassNoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_id;

    @Column
    private String notice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private ClassEntity classEntity;

    public static ClassNoticeEntity toSaveNoticeEntity(ClassEntity classEntity, String notice){
        ClassNoticeEntity classNoticeEntity = new ClassNoticeEntity();

        classNoticeEntity.setNotice(notice);
        classNoticeEntity.setClassEntity(classEntity);
        return classNoticeEntity;
    }
}
