package com.example.Plowithme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.Date;

@Entity
@Data
public class UploadFile {

    @Id
    @GeneratedValue
    int id;

    @Column
    String fileName;

    @Column
    String saveFileName;

    @Column
    String filePath;

    @Column
    String contentType;

    @Column
    long size;

    Date regDate;
}
