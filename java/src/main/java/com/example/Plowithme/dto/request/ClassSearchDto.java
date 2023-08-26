package com.example.Plowithme.dto.request;


import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSearchDto {

    private String title;

    private int member; // 설정해둔 인원수

    private int status; // 현재 참여한 인원수

    private String start_region; //출발 위치

    private String description;

    private String start_date;



    public static ClassSearchDto toDto(ClassEntity classEntity) {
        return new ClassSearchDto(classEntity.getTitle(),
                classEntity.getMember(), classEntity.getStatus(), classEntity.getStart_region(), classEntity.getDescription(), classEntity.getStart_date());
    }
}
