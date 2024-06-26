package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.entity.CategoryEntity;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.repository.CategoryRepository;
import com.course_platform.courses.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceTest {
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private FileService fileService;
    @Autowired
    private CourseService courseService;
    private CourseRequest courseRequest;
    private CourseEntity courseEntity;
    private ObjectMapper objectMapper;
    private HttpHeaders headers;
    private String accessToken;
    private CategoryEntity category;
    private Pageable pageable;
    private Sort sort;
    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    void init(){
        objectMapper = new ObjectMapper();
        headers  = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);
        courseRequest = CourseRequest.builder()
                .title("Khóa học js")
                .price(0)
                .description("khoa hoc kien thuc co ban cho ngon ngu lap trinh javascript")
                .imageCourse("https://th.bing.com/th/id/R.4c48053ed95e28a2ec7c6e3b784b1c16?rik=%2fBj8KWCE8bCapQ&riu=http%3a%2f%2fthebamboocode.com%2fwp-content%2fuploads%2f2016%2f03%2fjs-logo.png&ehk=g5YFs6jfGflo%2brZl5LTWfOfewU3YxawHs6HXpKV9faE%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1")
                .categoryId("36cc2afd-b14e-4723-84fb-48b2034f445d")
                .build();
        category = CategoryEntity.
                builder()
                .id("36cc2afd-b14e-4723-84fb-48b2034f445d")
                .name("technology")
                .build();
        courseEntity = CourseEntity.builder()
                .id("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .title("Khóa học js")
                .price(0)
                .description("khoa hoc kien thuc co ban cho ngon ngu lap trinh javascript")
                .imageCourse("https://th.bing.com/th/id/R.4c48053ed95e28a2ec7c6e3b784b1c16?rik=%2fBj8KWCE8bCapQ&riu=http%3a%2f%2fthebamboocode.com%2fwp-content%2fuploads%2f2016%2f03%2fjs-logo.png&ehk=g5YFs6jfGflo%2brZl5LTWfOfewU3YxawHs6HXpKV9faE%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1")
                .category(category)
                .build();
    }
    @Test
    void createCourse_validRequest_success() throws Exception {
        // GIVEN
        courseRequest.setCategoryId("36cc2afd-b14e-4723-84fb-48b2034f445d");
        when(fileService.createFolder(anyString())).thenReturn("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp");
        when(categoryRepository.findById(anyString())).thenReturn(Optional.ofNullable(category));
        when(courseRepository.save(any())).thenReturn(courseEntity);
        // WHEN
        var response = courseService.create(courseRequest);
        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp");
        Assertions.assertThat(response.getTitle()).isEqualTo("Khóa học js");
    }
    @Test
    void createCourse_invalidUploadFile_fail() throws Exception {
        // GIVEN

        doThrow(new CustomRuntimeException(ErrorCode.UNUPLOADFILE_EXCEPTION)).when(fileService).createFolder(anyString());

        // WHEN
        var exception = assertThrows(CustomRuntimeException.class,() -> courseService.create(courseRequest));
        // THEN

        Assertions.assertThat(exception.getErrorCode().getMessage()).isEqualTo("Upload file failed");

    }
    @Test
    void updateCourse_validRequest_success(){
        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        String title = "Khóa học html,css";
        courseRequest.setTitle(title);
        doNothing().when(fileService).updateFolder(anyString(), anyString());
        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(courseEntity));

        when(courseRepository.save(courseEntity)).thenReturn(courseEntity);
        // WHEN
        var response = courseService.update(courseId,courseRequest);
        // THEN
       Assertions.assertThat(response.getId()).isEqualTo(courseId);
        Assertions.assertThat(response.getTitle()).isEqualTo("Khóa học html,css");
    }
    @Test
    void updateCourse_courseNotFound_fail(){

        // GIVEN
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faG";
        // WHEN
        var exception = assertThrows(CustomRuntimeException.class,() -> courseService.update(courseId,courseRequest));
        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        Assertions.assertThat(exception.getErrorCode().getMessage()).isEqualTo("Course not found");
    }
    @Test
    void updateCourse_categoryNotFound_fail(){
        String courseId = "1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp";
        // GIVEN
        courseRequest.setCategoryId("");

        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(courseEntity));
        when(courseRepository.save(courseEntity)).thenReturn(courseEntity);
        // WHEN
        var exception = assertThrows(CustomRuntimeException.class,() -> courseService.update(courseId,courseRequest));
        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        Assertions.assertThat(exception.getErrorCode().getMessage()).isEqualTo("Category not found");
    }
    @Test
    void getCourse_validRequest_success(){

        // GIVEN
        pageable = PageRequest.of(0,5);
        List<CourseEntity> courses = List.of(courseEntity);
        Page<CourseEntity> page = new PageImpl<>(courses,pageable,courses.size());
        when(courseRepository.findAll(pageable)).thenReturn(page);

        // WHEN
        var response = courseService.findAll(pageable);
        // THEN
        Assertions.assertThat(response.getPagination().getPage())
                .isEqualTo(1);

    }
}
