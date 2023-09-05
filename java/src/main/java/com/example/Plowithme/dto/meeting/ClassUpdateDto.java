package com.example.Plowithme.dto.meeting;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClassUpdateDto {

    private String title;

    private LocalDateTime inst_date;

    private Integer member_max;

    private Integer member_current;

    private int status;

    private String startRegion;

    private String end_region;

    private String description;

    private String start_date;

    private String end_date;

    private Integer start_year;

    private Integer start_month;

    private Integer start_day;


    private Long maker_id;

    /** 모임 상세 페이지에서 사용 */
    private String notice;

    private Double distance;
}
