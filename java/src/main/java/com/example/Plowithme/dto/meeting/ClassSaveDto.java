package com.example.Plowithme.dto.meeting;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassSaveDto {

    private String title;

    private Integer member_max;

    private String startRegion;

    private String end_region;

    private String notice;

    private String description;

    private String start_date;

    private String end_date;

    private Integer start_year;

    private Integer start_month;

    private Integer start_day;

    private Double distance;

    private String image_name;

    private Long maker_id;

}

