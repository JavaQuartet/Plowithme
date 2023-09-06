package com.example.Plowithme.dto.community;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class BoardSaveDto {
    private String title;
    private String contents;
    private Integer category;

    @Nullable
    private MultipartFile file;

}
