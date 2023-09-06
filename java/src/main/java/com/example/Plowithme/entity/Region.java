package com.example.Plowithme.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class Region {

    private String depth_1; // 도/시

    private String depth_2; // 군/구

    private String depth_3; // 법정동/법정리

    private String address; // 전체 주소


}
