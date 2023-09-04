package com.example.Plowithme;

import com.example.Plowithme.controller.ClassController;
import com.example.Plowithme.dto.request.meeting.ClassDTO;
import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

public class dumy {



    //지역 랜덤 생성
    public static String nNick() {
        List<String> 시 = Arrays.asList("서울 ", "부산 ", "경기도 ", "광주 ", "대전 ", "대구 ");
        List<String> 구 = Arrays.asList("종로구 ", "서대문구 ", "강남구 ", "강동구 ", "강북구 ", "관악구 ", "구로구 ");
        List<String> 동 = Arrays.asList("영천동", "신사동", "논현동");
        Collections.shuffle(시);
        Collections.shuffle(구);
        Collections.shuffle(동);
        String text = 시.get(0) + 구.get(0) + 동.get(0);
        return text;
    }

    //id 랜덤 생성
    public static String nId() {
        String text = "";
        String ran = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < 6; i++) {
            text += ran.charAt((int)(Math.random() * ran.length()));
        }
        return text;
    }



    public static String nNo2() {
        return (int)(Math.random() * 99)+1 +"";
    }

}
