package com.example.Plowithme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Date {

    @Column(insertable=false, updatable=false)
    private int year;

    @Column(insertable=false, updatable=false)
    private int month;

    @Column(insertable=false, updatable=false)
    private int day;

    protected Date(){
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
