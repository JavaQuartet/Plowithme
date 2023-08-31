package com.example.Plowithme.dto.request.meeting;


import com.example.Plowithme.dto.request.mypage.MessageFindDto;
import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.Date;
import com.example.Plowithme.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassFindDto {

    private Long class_Id;

    private String title;

    private int member_max; // 설정해둔 인원수

    private int member_current; // 현재 참여한 인원수

    private String startRegion; //출발 위치

    private String description;

    private String start_date;


    public static ClassFindDto toDto(ClassEntity classEntity) {
        return new ClassFindDto(classEntity.getId(),classEntity.getTitle(),classEntity.getMember_max(), classEntity.getMember_current(),
                classEntity.getStart_region(), classEntity.getDescription(), classEntity.getStart_date());
    }

}
