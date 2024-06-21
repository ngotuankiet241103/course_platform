package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.SectionRequest;
import com.course_platform.courses.dto.response.Section;
import com.course_platform.courses.service.SectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestConfig.class)
public class SectionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SectionService sectionService;
    @Autowired
    private AuthRest authRest;
    private HttpHeaders headers;
    private String accessToken;
    private ObjectMapper objectMapper;
    private SectionRequest sectionRequest;
    private Section section;
    @BeforeEach
    void initData(){
        objectMapper = new ObjectMapper();
        accessToken = authRest.login();
        headers  = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);
        sectionRequest = SectionRequest.builder()
                .title("Gioi thieu khoa hoc")
                .courseId("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .build();
        section = Section.builder()
                .id("757646e7-e2e2-49c1-a100-6579c758cc71")
                .lessons(new ArrayList<>())
                .title("Gioi thieu khoa hoc")
                .build();
    }

    @Test
    void createSection_validRequest_success() throws Exception{
        // GIVEN
        String content = objectMapper.writeValueAsString(sectionRequest);
        when(sectionService.create(any())).thenReturn(section);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sections")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("757646e7-e2e2-49c1-a100-6579c758cc71"));;
    }
    @Test
    void createSection_invalidTitle_fail() throws Exception{
        // GIVEN
        sectionRequest.setTitle("");
        String content = objectMapper.writeValueAsString(sectionRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sections")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));;
    }
    @Test
    void getSectionByCourse_validRequest_success() throws Exception{
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        when(sectionService.findByCourseCode(anyString())).thenReturn(new ArrayList<>(List.of(section)));
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sections/" + courseId + "/course")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));

    }
    @Test
    void getSectionByCourse_invalidParam_fail() throws Exception{
        // GIVEN
        String courseId = "";

        when(sectionService.findByCourseCode(anyString())).thenReturn(new ArrayList<>(List.of(section)));
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sections/" + courseId + "/course")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                        .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(1005))
                        .andExpect(MockMvcResultMatchers.jsonPath("message").value("Method is not supported"));

    }
    @Test
    void updateSection_validRequest_success() throws Exception{
        // GIVEN
        String title = "Mo dau khoa hoc";
        sectionRequest.setTitle(title);
        section.setTitle(title);
        String sectionId = "757646e7-e2e2-49c1-a100-6579c758cc71";
        String content = objectMapper.writeValueAsString(sectionRequest);
        when(sectionService.update(anyString(),any())).thenReturn(section);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sections/" + sectionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("757646e7-e2e2-49c1-a100-6579c758cc71"))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.title").value(title));

    }
    @Test
    void updateSection_invalidParam_fail() throws Exception{
        // GIVEN
        String sectionId = "";
        String content = objectMapper.writeValueAsString(sectionRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sections/" + sectionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Resource not found"));

    }
    @Test
    void updateSection_invalidTitle_fail() throws Exception{
        // GIVEN
        String sectionId = "757646e7-e2e2-49c1-a100-6579c758cc71";
        sectionRequest.setTitle("");
         String content = objectMapper.writeValueAsString(sectionRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sections/" + sectionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));

    }
}
