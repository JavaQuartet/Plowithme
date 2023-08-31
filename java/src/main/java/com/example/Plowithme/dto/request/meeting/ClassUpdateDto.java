package com.example.Plowithme.dto.request.meeting;

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
    // public LocalDateTime updt_date; 모임에 수정날짜는 불필요 하지 않나요?

    private Integer member_max; // 설정해둔 인원수

    private Integer member_current;

    private int status;

    private String start_region; //출발 위치

    private String end_region; //도착 위치

    private String description;

    private String start_date;

    private String end_date;

    private Integer start_year;

    private Integer start_month;

    private Integer start_day;


    private Long maker_id; // 모임 만든사람 아이디

    // 모임 상세 페이지에서 사용
    private String notice; //모임 공지사항

    private Double distance; // 걸은 거리
}
