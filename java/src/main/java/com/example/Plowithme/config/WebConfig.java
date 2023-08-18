package com.example.Plowithme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //CORS 설정
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**");

    }
//    //파일 업로드 설정
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/profileUploads/**");
//    }
}
