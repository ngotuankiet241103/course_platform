package com.course_platform.courses.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;


public class AuthRest {
    @Autowired
    private CustomRestTemplate<Map> restTemplate ;
    @Value("${test.oauth2.token}")
    private String url;
    @Value("${test.oauth2.username}")
    private String username;
    @Value("${test.oauth2.password}")
    private String password;
    @Value("${test.oauth2.grantType}")
    private String grant_type;
    @Value("${test.oauth2.clientId}")
    private String client_id;
    public String login(){
        HttpEntity<?> object = initData();
        ResponseEntity<?> response = restTemplate.post(url,object,Map.class);
        Map<?,?> result = (Map<?, ?>) response.getBody();
        return result.get("access_token").toString();
    }
    private HttpEntity<?> initData(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body= new LinkedMultiValueMap<>();
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", grant_type);
        body.add("client_id", client_id);

        return new HttpEntity<>(body, headers);
    }
}
