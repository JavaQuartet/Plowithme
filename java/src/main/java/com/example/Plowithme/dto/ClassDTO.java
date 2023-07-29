package com.example.Plowithme.dto;

import com.example.Plowithme.Entity.ClassEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    private Long id;


    // 모임 만들때 사용
    private String title;
    private LocalDateTime inst_date;
    // public LocalDateTime updt_date; 모임에 수정날짜는 불필요 하지 않나요?
    private int member; // 설정해둔 인원수
    private int status; // 현재 참여한 인원수
    private String start_region; //출발 위치
    private String end_region; //도착 위치
    private String description;
    private LocalDateTime start_date;
    private LocalDateTime end_date;


    // 모임 상세 페이지에서 사용
    private String notion; //모임 공지사항

    public static ClassDTO toClassDTO(ClassEntity classEntity){
        ClassDTO classDTO = new ClassDTO();

        classDTO.setId(classEntity.getId());
        classDTO.setTitle(classEntity.getTitle());
        classDTO.setMember(classEntity.getMember());
        classDTO.setStatus(1);
        classDTO.setStart_region(classEntity.getStart_region());
        classDTO.setEnd_region(classEntity.getEnd_region());
        classDTO.setDescription(classEntity.getDescription());
        return classDTO;
    }
}
