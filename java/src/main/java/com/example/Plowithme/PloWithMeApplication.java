package com.example.Plowithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class PloWithMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PloWithMeApplication.class, args);

    }
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        return new HiddenHttpMethodFilter();
    }
}
