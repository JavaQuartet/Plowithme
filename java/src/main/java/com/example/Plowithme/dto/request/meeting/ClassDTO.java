package com.example.Plowithme.dto.request.meeting;

import com.example.Plowithme.entity.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {

    /*private Long id;*/


    // 모임 수정에 사용
    private String title;

    /*private LocalDateTime inst_date;*/
    // public LocalDateTime updt_date; 모임에 수정날짜는 불필요 하지 않나요?

    private Integer member_max; // 설정해둔 인원수

    private Integer member_current;

    private int status;

    private String startRegion; //출발 위치

    private String end_region; //도착 위치

    private String description;

    private String start_date;

    private String end_date;

    private Integer start_year;

    private Integer start_month;

    private Integer start_day;

/*    private MultipartFile classFile; // save.html -> Controller 파일 담는 용도

    private String origialFileName; // 원본 파일 이름

    private String storedFileName; // 서버 저장용 파일 이름*/



    private Long maker_id; // 모임 만든사람 아이디

    // 모임 상세 페이지에서 사용
    private String notice; //모임 공지사항

    private Double distance; // 걸은 거리

    private String image_url;
    /*private Region region; // 지역 태그*/

    // 참여자 모임
    private List<ClassParticipantsEntity> classParticipantsEntityList = new ArrayList<>();

    private List<ClassNoticeEntity> classNoticeEntityList = new ArrayList<>();


    public static ClassDTO toClassDTO(ClassEntity classEntity){
        ClassDTO classDTO = new ClassDTO();

        /*classDTO.setId(classEntity.getId());*/
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

        classDTO.setImage_url(classEntity.getImageUrl(classEntity.getImage_name()));

        classDTO.setClassParticipantsEntityList(classEntity.getClassParticipantsEntityList());
        classDTO.setClassNoticeEntityList(classEntity.getClassNoticeEntityList());

        return classDTO;
    }
}
