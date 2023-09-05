package com.example.Plowithme.dto.meeting;

import com.example.Plowithme.entity.ClassEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassFindDto {

    private Long class_Id;

    private String title;

    private Integer member_max;

    private Integer member_current;

    private String startRegion;

    private String description;

    private String start_date;

    private Integer start_year;

    private Integer start_month;

    private Integer start_day;


    public static ClassFindDto toDto(ClassEntity classEntity) {
        return new ClassFindDto(classEntity.getId(),classEntity.getTitle(),classEntity.getMember_max(), classEntity.getMember_current(),
                classEntity.getStartRegion(), classEntity.getDescription(), classEntity.getStart_date(), classEntity.getStart_year(), classEntity.getStart_month(), classEntity.getStart_day());
    }

}
