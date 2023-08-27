package com.example.Plowithme.dto.request.meeting;


import com.example.Plowithme.entity.ClassEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
