package com.example.Plowithme.dto.community;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveDto {

    private String contents;

    @JsonProperty("post_id")
    private Long postId;



}
