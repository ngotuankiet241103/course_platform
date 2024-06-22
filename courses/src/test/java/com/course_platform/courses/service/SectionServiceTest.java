package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.SectionRequest;

import com.course_platform.courses.dto.response.Section;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.SectionEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.SectionRepository;

import  static  org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringBootTest
public class SectionServiceTest {
    @MockBean
    private SectionRepository sectionRepository;
    @MockBean
    private CourseRepository courseRepository;
    private SectionRequest sectionRequest;
    private SectionEntity sectionEntity;
    private CourseEntity courseEntity;
    @Autowired
    private SectionService sectionService;
    private Section section;
    private  String title;
    private Authentication authentication;
    @Value("${test.oauth2.username}")
    private String username;
    @Value("${test.oauth2.password}")
    private String password;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    void initData(){
        title = "Mo dau bai hoc";
        courseEntity = CourseEntity.builder()
                .id("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .title("Khóa học js")
                .price(0)
                .description("khoa hoc kien thuc co ban cho ngon ngu lap trinh javascript")
                .imageCourse("https://th.bing.com/th/id/R.4c48053ed95e28a2ec7c6e3b784b1c16?rik=%2fBj8KWCE8bCapQ&riu=http%3a%2f%2fthebamboocode.com%2fwp-content%2fuploads%2f2016%2f03%2fjs-logo.png&ehk=g5YFs6jfGflo%2brZl5LTWfOfewU3YxawHs6HXpKV9faE%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1")
                .build();

        sectionRequest = SectionRequest.builder()
                .title("Gioi thieu khoa hoc")
                .courseId("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .build();
        sectionEntity = SectionEntity.builder()
                .id("757646e7-e2e2-49c1-a100-6579c758cc71")
                .lessons(new ArrayList<>())
                .title("Gioi thieu khoa hoc")
                .course(courseEntity)
                .build();
        section = Section
                .builder()
                .id("757646e7-e2e2-49c1-a100-6579c758cc71")
                .lessons(new ArrayList<>())
                .title(title)
                .build();
        authentication = new UsernamePasswordAuthenticationToken(username,password, List.of(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication.getName());
    }
    @Test
    void createSection_validRequest_success(){
        // GIVEN
        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(courseEntity));
        when(sectionRepository.save(any())).thenReturn(sectionEntity);
        // WHEN
        var response = sectionService.create(sectionRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("757646e7-e2e2-49c1-a100-6579c758cc71");
        assertThat(response.getTitle()).isEqualTo("Gioi thieu khoa hoc");
    }
    @Test
    void createSection_courseNotFound_fail(){
        // GIVEN
        sectionRequest.setCourseId("");
        // WHEN
        var exception= assertThrows(CustomRuntimeException.class,() -> sectionService.create(sectionRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Course not found");

    }
    @Test
    void updateSection_courseNotFound_fail(){
        // GIVEN
        String sectionId = "757646e7-e2e2-49c1-a100-6579c758cc71";
        when(sectionRepository.existsById(anyString())).thenReturn(false);
        // WHEN
        var exception= assertThrows(CustomRuntimeException.class,() -> sectionService.update(sectionId,sectionRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Section not found");

    }
    @Test
    void updateSection_validRequest_success(){
        // GIVEN
        String sectionId = "757646e7-e2e2-49c1-a100-6579c758cc71";

        sectionEntity.setTitle(title);
        when(sectionRepository.existsById(anyString())).thenReturn(true);
        when(sectionRepository.updateTitle(anyString(),anyString())).thenReturn(sectionEntity);

        // WHEN
        var response = sectionService.update(sectionId,sectionRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("757646e7-e2e2-49c1-a100-6579c758cc71");
        assertThat(response.getTitle()).isEqualTo(title);

    }
    @Test
    void getSectionByCourse_validRequest_success(){
        // GIVEN
        String courseCode = "khoa-hoc-js";
        when(sectionRepository.findByCourseCodeOrderByPosition(courseCode)).thenReturn(new ArrayList<>(List.of(sectionEntity)));

        // WHEN
        var response = sectionService.findByCourseCode(courseCode);
        // THEN
        assertThat(response.stream().anyMatch(section -> section.getId().equals("757646e7-e2e2-49c1-a100-6579c758cc71"))).isEqualTo(true);

    }

}
