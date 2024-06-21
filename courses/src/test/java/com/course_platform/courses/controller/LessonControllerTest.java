package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.LessonRequest;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.file.CustomMultipartFile;
import com.course_platform.courses.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(RestConfig.class)
public class LessonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LessonService lessonService;
    private LessonRequest lessonRequest;
    private Lesson lesson;
    private String accessToken;
    private HttpHeaders headers;
    @Autowired
    private AuthRest authRest;
    private ObjectMapper objectMapper;
    @Value("${api.prefix}/lessons")
    private String api;
    @BeforeEach
    void initData(){
        objectMapper = new ObjectMapper();
        accessToken = authRest.login();
        headers  = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);
        lessonRequest = LessonRequest.builder()
                .title("promise la gi")
                .sectionId("757646e7-e2e2-49c1-a100-6579c758cc71")
                .courseId("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .file(new CustomMultipartFile())
                .build();
        lesson = Lesson.builder()
                .id("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7")
                .title("promise la gi")
                .code("promise-la-gi")
                .video("1Q3aSNn-6CPpFLZTJs8sWpDYYg1KzhQSv")
                .isBlock(true)
                .isCompleted(false)
                .build();
    }
    @Test
    void createLesson_validRequest_Success() throws Exception{
        // GIVEN
        String content = objectMapper.writeValueAsString(lessonRequest);
        when(lessonService.create(any())).thenReturn(lesson);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7"));;
    }
    @Test
    void createLesson_invalidTitle_fail() throws Exception{
        // GIVEN
        lessonRequest.setTitle("");
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));;
    }
    @Test
    void createLesson_invalidCourse_fail() throws Exception{
        // GIVEN
        lessonRequest.setCourseId("");
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Course must be not empty"));;
    }
    @Test
    void createLesson_invalidSection_fail() throws Exception{
        // GIVEN
        lessonRequest.setSectionId("");
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Section must be not empty"));;
    }
    @Test
    void updateLesson_validRequest_success() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        lessonRequest.setSectionId("");
        String content = objectMapper.writeValueAsString(lessonRequest);
        when(lessonService.updateInfo(lessonId,lessonRequest)).thenReturn(lesson);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/" + lessonId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7"));;
    }
    @Test
    void updateLesson_invalidParam_fail() throws Exception{
        // GIVEN
        String lessonId = "";

        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/" + lessonId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Resource not found"));;
    }
    @Test
    void updateLesson_invalidSection_fail() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        lessonRequest.setSectionId("");
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/" + lessonId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Section must be not empty"));;
    }
    @Test
    void updateLesson_invalidCourse_fail() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        lessonRequest.setSectionId("");
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/" + lessonId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Course must be not empty"));;
    }
    @Test
    void updateLesson_invalidTitle_fail() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        lessonRequest.setSectionId("");
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/" + lessonId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));;
    }
    @Test
    void swapLesson_invalidTitle_fail() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        lessonRequest.setSectionId("");
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/" + lessonId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));;
    }

}
