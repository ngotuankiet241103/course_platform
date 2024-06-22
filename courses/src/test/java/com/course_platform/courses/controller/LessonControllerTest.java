package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.LessonRequest;
import com.course_platform.courses.dto.request.LessonSwapRequest;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.file.CustomMultipartFile;
import com.course_platform.courses.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private LessonSwapRequest lessonSwapRequest;
    @Value("${api.prefix}/lessons")
    private String api;
    private MultiValueMap<String,String> params;
    private MockMultipartFile file;
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
                .file(null)
                .build();
        lesson = Lesson.builder()
                .id("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7")
                .title("promise la gi")
                .code("promise-la-gi")
                .video("1Q3aSNn-6CPpFLZTJs8sWpDYYg1KzhQSv")
                .isBlock(true)
                .position(10)
                .isCompleted(false)
                .build();
        lessonSwapRequest = LessonSwapRequest.builder()
                .lessonId("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7")
                .toPosition(11)
                .srcSectionId("8c91822a-b665-4cec-a68b-de6047979a48")
                .desSectionId("8c91822a-b665-4cec-a68b-de6047979a48")
                .courseId("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .build();
        params = new LinkedMultiValueMap<>();
        params.add("title", lessonRequest.getTitle());
        params.add("sectionId", lessonRequest.getSectionId());
        params.add("courseId", lessonRequest.getCourseId());
        file = new MockMultipartFile(
                "file",
                "bai1.mp4",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }

    @Test
    void createLesson_validRequest_Success() throws Exception{
        // GIVEN
        when(lessonService.create(any())).thenReturn(lesson);

        // WHEN,THEN
        mockMvc.perform( MockMvcRequestBuilders.multipart(api)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7"));;
    }
    @Test
    void createLesson_invalidTitle_fail() throws Exception{
        // GIVEN
        params.replace("title", List.of(""));

        // WHEN,THEN
        mockMvc.perform( MockMvcRequestBuilders.multipart(api)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));;
    }
    @Test
    void createLesson_invalidCourse_fail() throws Exception{
        // GIVEN
        params.replace("courseId", List.of(""));

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart(api)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Course must be not empty"));;
    }
    @Test
    void createLesson_invalidSection_fail() throws Exception{
        // GIVEN
        params.replace("sectionId", List.of(""));
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart(api)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Section must be not empty"));;
    }
    @Test
    void updateLesson_validRequest_success() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";

        when(lessonService.updateInfo(lessonId,lessonRequest)).thenReturn(lesson);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,api + "/{lesson-id}",lessonId)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));

    }
    @Test
    void updateLesson_invalidParam_fail() throws Exception{
        // GIVEN
        String lessonId = "";

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,api + "/{lesson-id}",lessonId)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Resource not found"));;
    }
    @Test
    void updateLesson_invalidSection_fail() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        params.replace("sectionId", List.of(""));
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,api + "/{lesson-id}",lessonId)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Section must be not empty"));;
    }
    @Test
    void updateLesson_invalidCourse_fail() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        params.replace("courseId", List.of(""));


        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,api + "/{lesson-id}",lessonId)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Course must be not empty"));;
    }
    @Test
    void updateLesson_invalidTitle_fail() throws Exception{
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        params.replace("title", List.of(""));
        String content = objectMapper.writeValueAsString(lessonRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,api + "/{lesson-id}",lessonId)
                        .file(file)
                        .params(params)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));
    }
    @Test
    void swapLesson_validRequest_success() throws Exception{
        // GIVEN
        String content = objectMapper.writeValueAsString(lessonSwapRequest);
        when(lessonService.swapLesson(lessonSwapRequest)).thenReturn(lesson);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/swap")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));
    }
    @Test
    void swapLesson_invalidLesson_fail() throws Exception{
        // GIVEN
        lessonSwapRequest.setLessonId("");
        String content = objectMapper.writeValueAsString(lessonSwapRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/swap")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Lesson must be not empty"));;
    }
    @Test
    void swapLesson_invalidSectionSrc_fail() throws Exception{
        // GIVEN
        lessonSwapRequest.setSrcSectionId("");
        String content = objectMapper.writeValueAsString(lessonSwapRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/swap")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Section source must be not empty"));
    }
    @Test
    void swapLesson_invalidSectionDes_fail() throws Exception{
        // GIVEN
        lessonSwapRequest.setDesSectionId("");
        String content = objectMapper.writeValueAsString(lessonSwapRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/swap")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Section des must be not empty"));;
    }
    @Test
    void swapLesson_invalidCourse_fail() throws Exception{
        // GIVEN
        lessonSwapRequest.setCourseId("");
        String content = objectMapper.writeValueAsString(lessonSwapRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/swap")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Course must be not empty"));;
    }
    @Test
    void swapLesson_invalidPosition_success() throws Exception{
        // GIVEN
        lessonSwapRequest.setToPosition(0);
        String content = objectMapper.writeValueAsString(lessonSwapRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/swap")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Position must be greater than 0"));;
    }

}
