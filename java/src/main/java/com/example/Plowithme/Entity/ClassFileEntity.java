package com.example.Plowithme.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "class_file_table")
public class ClassFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    public static ClassFileEntity toClassFileEntity(ClassEntity classEntity, String originalFileName, String storedFileName){
        ClassFileEntity classFileEntity = new ClassFileEntity();
        classFileEntity.setOriginalFileName(originalFileName);
        classFileEntity.setStoredFileName(storedFileName);
        classFileEntity.setClassEntity(classEntity);
        return classFileEntity;
    }


}
