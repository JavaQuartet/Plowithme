package com.example.Plowithme.dto.community;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter //인스턴스 변수 반환
@Setter //인스턴스 변수를 대입하거나 수정
@ToString
@Data
@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor //필드를 모두 매개변수 라는 생성자 만들어 줌
public class CommentSaveDto {

    private String contents;

    @JsonProperty("post_id")
    private Long postId;



}
