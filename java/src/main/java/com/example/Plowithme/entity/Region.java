package com.example.Plowithme.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Region {


    private String depth_1; // 도/시

    private String depth_2; // 군/구

    private String depth_3; // 법정동/법정리

    private String address; // 전체 주소
    protected Region() {
    }
    public Region(String depth_1, String depth_2, String depth_3, String address) {
        this.depth_1 = depth_1;
        this.depth_2 = depth_2;
        this.depth_3 = depth_3;
        this.address = address;
    }



}
