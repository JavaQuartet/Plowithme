package com.example.Plowithme.dto.request.meeting;

import com.example.Plowithme.entity.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassSaveDto {

//    @NotEmpty
//    @Size(max = 10)
    private String title;

//    @Min(2)
    private Integer member_max; // 설정해둔 인원수

//    @NotEmpty
    private String startRegion; //출발 위치

//    @NotEmpty
    private String end_region; //도착 위치

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

