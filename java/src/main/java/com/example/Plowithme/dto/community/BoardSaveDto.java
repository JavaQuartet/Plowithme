package com.example.Plowithme.dto.community;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;


@Data
public class BoardSaveDto {

    private String title;

    private String contents;

    private Integer category;

    @Nullable
    private MultipartFile file;

}
