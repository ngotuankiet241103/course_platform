package com.course_platform.courses.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;


public class CustomRestTemplate<T> {
    @Autowired
    private org.springframework.web.client.RestTemplate restTemplate;
    public ResponseEntity<T> post(String url, HttpEntity<?> object,Class<T> t){
        return  restTemplate.postForEntity(url,object,t);
    }

}
