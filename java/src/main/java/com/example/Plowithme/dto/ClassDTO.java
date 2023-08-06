package com.example.Plowithme.dto;

import com.example.Plowithme.entity.ClassEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private String start_date;
    private String end_date;

    private MultipartFile classFile; // save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    private int classjoined; // 모임 참여 여부(참여 1, 미참여 0)


    // 모임 상세 페이지에서 사용
    private String notion; //모임 공지사항



    public static ClassDTO toClassDTO(ClassEntity classEntity){
        ClassDTO classDTO = new ClassDTO();

        classDTO.setId(classEntity.getId());
        classDTO.setTitle(classEntity.getTitle());
        classDTO.setMember(classEntity.getMember());
        classDTO.setStatus(classEntity.getStatus());
        classDTO.setStart_region(classEntity.getStart_region());
        classDTO.setEnd_region(classEntity.getEnd_region());
        classDTO.setDescription(classEntity.getDescription());
        classDTO.setStart_date(classEntity.getStart_date());
        classDTO.setEnd_date(classEntity.getEnd_date());
        classDTO.setNotion(classEntity.getNotice());
        classDTO.setClassjoined(classEntity.getClassjoined());
        return classDTO;
    }
}
