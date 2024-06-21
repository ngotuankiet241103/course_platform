package com.course_platform.courses.auth;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@TestConfiguration
public class RestConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return  restTemplateBuilder.build();
    }
    @Bean
    public AuthRest authRest(){
        return new AuthRest();
    }
    @Bean
    public CustomRestTemplate customeRestTemplate(){
        return new CustomRestTemplate();
    }

}
