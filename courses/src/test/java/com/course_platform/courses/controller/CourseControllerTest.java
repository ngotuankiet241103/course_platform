package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.dto.response.Pagination;
import com.course_platform.courses.dto.response.Paging;
import com.course_platform.courses.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static  org.mockito.ArgumentMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(RestConfig.class)
public class CourseControllerTest {
    @MockBean
    private CourseService courseService;
    @Autowired
    private MockMvc mockMvc;
    private CourseRequest courseRequest;
    private Course course;
    private String accessToken;
    private  HttpHeaders headers;
    private Pageable pageable;
    private Sort sort;
    @Autowired
    private AuthRest authRest;
     private  ObjectMapper objectMapper;
    @BeforeEach
    void init(){
       objectMapper = new ObjectMapper();
       accessToken = authRest.login();
       headers  = new HttpHeaders();
       headers.add("Authorization","Bearer " + accessToken);

       courseRequest = CourseRequest.builder()
                .title("Khóa học js")
                .price(0)
                .description("khoa hoc kien thuc co ban cho ngon ngu lap trinh javascript")
                .imageCourse("https://th.bing.com/th/id/R.4c48053ed95e28a2ec7c6e3b784b1c16?rik=%2fBj8KWCE8bCapQ&riu=http%3a%2f%2fthebamboocode.com%2fwp-content%2fuploads%2f2016%2f03%2fjs-logo.png&ehk=g5YFs6jfGflo%2brZl5LTWfOfewU3YxawHs6HXpKV9faE%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1")
                .categoryId("36cc2afd-b14e-4723-84fb-48b2034f445d")
                .build();
        course = Course.builder()
                .id("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .title("Khóa học js")
                .price(0)
                .description("khoa hoc kien thuc co ban cho ngon ngu lap trinh javascript")
                .imageCourse("https://th.bing.com/th/id/R.4c48053ed95e28a2ec7c6e3b784b1c16?rik=%2fBj8KWCE8bCapQ&riu=http%3a%2f%2fthebamboocode.com%2fwp-content%2fuploads%2f2016%2f03%2fjs-logo.png&ehk=g5YFs6jfGflo%2brZl5LTWfOfewU3YxawHs6HXpKV9faE%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1")
                .build();
    }
    @Test
    void createCourse_validRequest_Success() throws Exception {
        // GIVEN

        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.create(ArgumentMatchers.any())).thenReturn(course);

        // WHEN,THEN

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(headers)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp"));

    }
    @Test
    void createCourse_invalidTitle_fail() throws Exception {
        //GIVEN
        courseRequest.setTitle("");

        String content = objectMapper.writeValueAsString(courseRequest);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));
    }
    @Test
    void createCourse_invalidDescription_fail() throws Exception {
        //GIVEN
        courseRequest.setDescription("");

        String content = objectMapper.writeValueAsString(courseRequest);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Description must be not empty"));
    }
    @Test
    void createCourse_invalidImage_fail() throws Exception {
        //GIVEN
        courseRequest.setImageCourse("");

        String content = objectMapper.writeValueAsString(courseRequest);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Image must be not empty"));
    }
    @Test
    void createCourse_invalidPrice_fail() throws Exception {
        //GIVEN
        courseRequest.setPrice(-1);

        String content = objectMapper.writeValueAsString(courseRequest);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Price must be greater than 0 or equals 0"));
    }
    @Test
    void updateCourse_validRequest_success() throws Exception{
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.update(anyString(),any())).thenReturn(course);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.
                put("/api/v1/courses/" +  courseId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(headers)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp"));;

    }
    @Test
    void updateCourse_invalidParam_success() throws Exception{
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.update(anyString(),any())).thenReturn(course);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/api/v1/courses/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Resource not found"));;

    }
    @Test
    void updateCourse_invalidTitle_fail() throws Exception{
        courseRequest.setTitle("");
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.update(anyString(),any())).thenReturn(course);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/api/v1/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Title must be not empty"));;

    }
    @Test
    void updateCourse_invalidDescription_fail() throws Exception{
        courseRequest.setDescription("");
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.update(anyString(),any())).thenReturn(course);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/api/v1/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Description must be not empty"));;

    }
    @Test
    void updateCourse_invalidImage_fail() throws Exception{
        courseRequest.setImageCourse("");
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.update(anyString(),any())).thenReturn(course);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/api/v1/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Image must be not empty"));;

    }
    @Test
    void updateCourse_invalidPrice_fail() throws Exception{
        courseRequest.setPrice(-1);
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.update(anyString(),any())).thenReturn(course);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/api/v1/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Price must be greater than 0 or equals 0"));;

    }
    @Test
    void getCourse_validRequest_Success() throws Exception {
        // GIVEN
        Paging<Course> response = Paging.<Course>builder()
                .data(List.of(course))
                .pagination(Pagination.builder().build())
                .build();
        String content = objectMapper.writeValueAsString(courseRequest);
        Mockito.when(courseService.findAll(pageable)).thenReturn(response);

        // WHEN,THEN

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));



    }

}
