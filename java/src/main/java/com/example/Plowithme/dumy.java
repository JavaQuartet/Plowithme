package com.example.Plowithme;

import com.example.Plowithme.entity.ClassEntity;
import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class dumy {

    public static String nNick() {
        List<String> 닉 = Arrays.asList("서울 ", "부산 ", "경기도 ", "광주 ", "대전 ", "대구 ");
        List<String> 네임 = Arrays.asList("종로구", "서대문구", "강남구", "강동구", "강북구", "관악구", "구로구");
        Collections.shuffle(닉);
        Collections.shuffle(네임);
        String text = 닉.get(0) + 네임.get(0);
        return text;
    }

}
