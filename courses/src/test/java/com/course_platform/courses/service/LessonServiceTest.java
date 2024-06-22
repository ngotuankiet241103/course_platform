package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.LessonRequest;
import com.course_platform.courses.dto.request.LessonSwapRequest;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.LessonEntity;
import com.course_platform.courses.entity.SectionEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.LessonRepository;
import com.course_platform.courses.repository.OrderRepository;
import com.course_platform.courses.repository.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class LessonServiceTest {
    @MockBean
    private LessonRepository lessonRepository;
    @Autowired
    private LessonService lessonService;
    @MockBean
    private FileService fileService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private SectionRepository sectionRepository;
    private LessonRequest lessonRequest;
    private Lesson lesson;
    private LessonSwapRequest lessonSwapRequest;
    private LessonEntity lessonEntity;
    private CourseEntity courseEntity;
    private SectionEntity sectionEntity;
    @BeforeEach
    void initData(){
        courseEntity = CourseEntity.builder()
                .id("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .build();
        sectionEntity = SectionEntity.builder()
                .id("757646e7-e2e2-49c1-a100-6579c758cc71")
                .build();
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
        lessonEntity = LessonEntity.builder()
                .id("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7")
                .title("promise la gi")
                .code("promise-la-gi")
                .video("1Q3aSNn-6CPpFLZTJs8sWpDYYg1KzhQSv")
                .course(courseEntity)
                .section(sectionEntity)
                .position(10)
                .build();
        lessonSwapRequest = LessonSwapRequest.builder()
                .lessonId("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7")
                .toPosition(11)
                .srcSectionId("8c91822a-b665-4cec-a68b-de6047979a48")
                .desSectionId("8c91822a-b665-4cec-a68b-de6047979a48")
                .courseId("1Nv1sCeybfaWm0ggiZfJwvD5f0iw5faGp")
                .build();
    }
    @Test
    void createLesson_validRequest_success(){
        // GIVEN
        when(fileService.uploadFile(any())).thenReturn("1Q3aSNn-6CPpFLZTJs8sWpDYYg1KzhQSv");
        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(new CourseEntity()));
        when(sectionRepository.findById(anyString())).thenReturn(Optional.ofNullable(new SectionEntity()));
        when(lessonRepository.save(any())).thenReturn(lessonEntity);
        when(orderRepository.findByIdCourseId(anyString())).thenReturn(new ArrayList<>());

        // WHEN
        var response = lessonService.create(lessonRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7");
        assertThat(response.getTitle()).isEqualTo("promise la gi");
    }
    @Test
    void createLesson_invalidFile_fail(){
        // GIVEN
        doThrow(new CustomRuntimeException(ErrorCode.FILE_INVALID)).when(fileService).uploadFile(any());

        // WHEN
        var response = assertThrows(CustomRuntimeException.class, () -> lessonService.create(lessonRequest));
        // THEN
        assertThat(response.getErrorCode().getCode()).isEqualTo(1003);
        assertThat(response.getErrorCode().getMessage()).isEqualTo("File must be not null");
    }
    @Test
    void createLesson_invalidCourse_fail(){
        // GIVEN
        when(fileService.uploadFile(any())).thenReturn("1Q3aSNn-6CPpFLZTJs8sWpDYYg1KzhQSv");
        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        // WHEN
        var response = assertThrows(CustomRuntimeException.class, () -> lessonService.create(lessonRequest));
        // THEN
        assertThat(response.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(response.getErrorCode().getMessage()).isEqualTo("Course not found");
    }
    @Test
    void createLesson_invalidSection_fail(){
        // GIVEN
        when(fileService.uploadFile(any())).thenReturn("1Q3aSNn-6CPpFLZTJs8sWpDYYg1KzhQSv");
        when(courseRepository.findById(anyString())).thenReturn(Optional.ofNullable(new CourseEntity()));
        when(sectionRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        // WHEN
        var response = assertThrows(CustomRuntimeException.class, () -> lessonService.create(lessonRequest));
        // THEN
        assertThat(response.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(response.getErrorCode().getMessage()).isEqualTo("Section not found");
    }
    @Test
    void updateLesson_invalidLesson_fail(){
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65";


        // WHEN
        var exception = assertThrows(CustomRuntimeException.class, () -> lessonService.updateInfo(lessonId,lessonRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Lesson not found");
    }
    @Test
    void updateLesson_validRequest_success(){
        // GIVEN
        String lessonId = "c8bb08a4-d240-4d55-b86b-e3b8a14a65d7";
        String title = "Promise là gì ?";

        lessonRequest.setTitle(title);
        when(lessonRepository.findById(anyString())).thenReturn(Optional.ofNullable(lessonEntity));
        when(lessonRepository.save(any())).thenReturn(lessonEntity);

        // WHEN
        var response =  lessonService.updateInfo(lessonId,lessonRequest);
        // THEN

        assertThat(response.getId()).isEqualTo("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7");
        assertThat(response.getTitle()).isEqualTo("Promise là gì ?");

    }
    @Test
    void swapLesson_validRequest_success(){
        // GIVEN

        when(lessonRepository.findById(anyString())).thenReturn(Optional.ofNullable(lessonEntity));
        doNothing().when(lessonRepository).updatePositionMoveDown(anyString(),anyInt(),anyInt(),anyString());
        when(lessonRepository.save(any())).thenReturn(lessonEntity);
        // WHEN
        var response =  lessonService.swapLesson(lessonSwapRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7");
        assertThat(response.getTitle()).isEqualTo("promise la gi");

    }
    @Test
    void swapLesson_invalidLesson_success(){
        // GIVEN
        lessonSwapRequest.setLessonId("");
        // WHEN
        var exception = assertThrows(CustomRuntimeException.class, () -> lessonService.swapLesson(lessonSwapRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Lesson not found");

    }
    @Test
    void swapLesson_invalidSection_success(){
        // GIVEN
        lessonSwapRequest.setDesSectionId("");
        when(lessonRepository.findById(anyString())).thenReturn(Optional.ofNullable(lessonEntity));


        // WHEN
        var exception = assertThrows(CustomRuntimeException.class, () -> lessonService.swapLesson(lessonSwapRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Section not found");

    }
}
