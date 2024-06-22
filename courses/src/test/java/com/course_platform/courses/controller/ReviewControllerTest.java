package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.dto.request.ReviewRequest;
import com.course_platform.courses.dto.response.Comment;
import com.course_platform.courses.dto.response.Review;
import com.course_platform.courses.dto.response.UserInfo;
import com.course_platform.courses.service.ReviewService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestConfig.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    private ReviewRequest reviewRequest;
    private Review review;
    private String accessToken;
    private HttpHeaders headers;
    @Autowired
    private AuthRest authRest;
    private ObjectMapper objectMapper;
    private CommentUpdateRequest commentUpdateRequest;
    @Value("${api.prefix}/reviews")
    private String api;
    private UserInfo userInfo;

    @BeforeEach
    void initData(){
        objectMapper = new ObjectMapper();
        accessToken = authRest.login();
        headers  = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);
        reviewRequest = ReviewRequest.builder()
                .review("Khoa hoc rat hay")
                .courseId("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .starRate(5)
                .build();
        userInfo = UserInfo.builder()
                .fullName("Ngo Kiet")
                .build();
        review = Review.builder()
                .id("07b6a0e7-e97c-41f2-83da-7ac7a2cbac75")
                .review("khoa hoc rat bo ich")
                .starRate(5)
                .user(userInfo)
                .createdDate(new Date())
                .build();

    }
    @Test
    void createReview_validRequest_success() throws Exception{
        // GIVEN
        String content = objectMapper.writeValueAsString(reviewRequest);
        when(reviewService.create(any())).thenReturn(review);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("07b6a0e7-e97c-41f2-83da-7ac7a2cbac75"));
    }
    @Test
    void createReview_invalidReview_fail() throws Exception{
        // GIVEN
        reviewRequest.setReview("");
        String content = objectMapper.writeValueAsString(reviewRequest);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Review must be not empty"));
    }
    @Test
    void createReview_invalidStarRateMin_fail() throws Exception{
        // GIVEN
        reviewRequest.setStarRate(0);
        String content = objectMapper.writeValueAsString(reviewRequest);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1002))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Rate min is 1"));
    }
    @Test
    void createReview_invalidStarRateMax_fail() throws Exception{
        // GIVEN
        reviewRequest.setStarRate(6);
        String content = objectMapper.writeValueAsString(reviewRequest);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1002))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Rate max is 5"));
    }
    @Test
    void createReview_invalidCourse_fail() throws Exception{
        // GIVEN
        reviewRequest.setCourseId("");
        String content = objectMapper.writeValueAsString(reviewRequest);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Course must be not empty"));
    }
    @Test
    void getReviewsByCourse_validRequest_success() throws Exception{
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        when(reviewService.findByCourseId(anyString())).thenReturn(new ArrayList<>(List.of(review)));
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.get(api + "/{course-id}/course", courseId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));

    }
}
