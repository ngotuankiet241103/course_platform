package com.course_platform.courses.service;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.dto.request.ReviewRequest;
import com.course_platform.courses.dto.response.Review;
import com.course_platform.courses.dto.response.UserInfo;
import com.course_platform.courses.entity.*;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.OrderRepository;
import com.course_platform.courses.repository.ReviewRepository;
import com.course_platform.courses.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(RestConfig.class)
public class ReviewServiceTest {
    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserRepository userRepository;
    private ReviewRequest reviewRequest;
    private ReviewEntity reviewEntity;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private AuthRest authRest;
    @Autowired
    private ReviewService reviewService;
    private String accessToken;
    private Authentication authentication;
    private CourseEntity courseEntity;
    @BeforeEach
    void initData(){
        accessToken = authRest.login();
        reviewRequest = ReviewRequest.builder()
                .review("Khoa hoc rat hay")
                .courseId("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .starRate(5)
                .build();
        courseEntity = CourseEntity.builder()
                .id("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .price(0)
                .title("Khoa hoc js")
                .build();
        reviewEntity = ReviewEntity.builder()
                .id("07b6a0e7-e97c-41f2-83da-7ac7a2cbac75")
                .review("khoa hoc rat bo ich")
                .starRate(5)
                .course(courseEntity)
                .build();

        authentication = new JwtAuthenticationToken(jwtDecoder.decode(accessToken));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
    @Test
    void createReview_validRequest_success(){
        // GIVEN
        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(courseEntity));
        when(orderRepository.existsByIdUserIdAndIdCourseId(anyString(),anyString())).thenReturn(true);
        when(reviewRepository.save(any())).thenReturn(reviewEntity);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(new UserEntity()));
        // WHEN
        var response = reviewService.create(reviewRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("07b6a0e7-e97c-41f2-83da-7ac7a2cbac75");

    }
    @Test
    void createReview_invalidCourse_fail(){
        // GIVEN
        reviewRequest.setCourseId("");
        // WHEN
        var exception = assertThrows(CustomRuntimeException.class,() -> reviewService.create(reviewRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Course not found");
    }
    @Test
    void createReview_invalidOrder_fail(){
        // GIVEN
        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(courseEntity));
        when(orderRepository.existsByIdUserIdAndIdCourseId(anyString(),anyString())).thenReturn(false);
        // WHEN
        var exception = assertThrows(CustomRuntimeException.class,() -> reviewService.create(reviewRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1003);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("You must be order this course before review this   ");
    }
    @Test
    void getReviewsByCourse_validRequest_success(){
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        when(reviewRepository.findByCourseId(anyString())).thenReturn(new ArrayList<>(List.of(reviewEntity)));
        // WHEN
        var response = reviewService.findByCourseId(courseId);
        // THEN
        assertThat(response.stream().anyMatch(review -> review.getId().equals("07b6a0e7-e97c-41f2-83da-7ac7a2cbac75"))).isEqualTo(true);
    }
}
