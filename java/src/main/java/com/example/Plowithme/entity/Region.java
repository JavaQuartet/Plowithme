package com.example.Plowithme.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Embeddable
@Getter
public class Region {


    private String depth_1; //시

    private String depth_2; //시+구

    private String depth_3; //동

    private String address;
    protected Region() {
    }
    public Region(String depth_1, String depth_2, String depth_3, String address) {
        this.depth_1 = depth_1;
        this.depth_2 = depth_2;
        this.depth_3 = depth_3;
        this.address = address;
    }



}
