package com.example.Plowithme.dto.meeting;

import com.example.Plowithme.entity.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {


    private Long class_Id;

    /** 모임 수정에 사용 */
    private String title;

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


    /** 참여자 모임 */
    private List<ClassParticipantsEntity> classParticipantsEntityList = new ArrayList<>();

    private List<ClassNoticeEntity> classNoticeEntityList = new ArrayList<>();


    public static ClassDTO toClassDTO(ClassEntity classEntity){
        ClassDTO classDTO = new ClassDTO();

        classDTO.setClass_Id(classEntity.getId());
        classDTO.setTitle(classEntity.getTitle());
        classDTO.setMember_max(classEntity.getMember_max());
        classDTO.setMember_current(classEntity.getMember_current());
        classDTO.setStatus(classEntity.getStatus());
        classDTO.setStartRegion(classEntity.getStartRegion());
        classDTO.setEnd_region(classEntity.getEnd_region());
        classDTO.setDescription(classEntity.getDescription());
        classDTO.setStart_date(classEntity.getStart_date());
        classDTO.setEnd_date(classEntity.getEnd_date());
        classDTO.setNotice(classEntity.getNotice());
        classDTO.setDistance(classEntity.getDistance());
        classDTO.setMaker_id(classEntity.getMaker_id());

        classDTO.setStart_year(classEntity.getStart_year());
        classDTO.setStart_month(classEntity.getStart_month());
        classDTO.setStart_day(classEntity.getStart_day());

        classDTO.setClassParticipantsEntityList(classEntity.getClassParticipantsEntityList());
        classDTO.setClassNoticeEntityList(classEntity.getClassNoticeEntityList());

        return classDTO;
    }
}
