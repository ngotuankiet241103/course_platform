package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.request.PageRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.service.CourseService;
import com.course_platform.courses.utils.Paging;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @PostMapping
    public ApiResponse<Course> create(@RequestBody @Valid CourseRequest courseRequest){
        return ApiResponse.<Course>builder()
                .result(courseService.create(courseRequest))
                .build();
    }
    @GetMapping
    ApiResponse<List<Course>> getCourses() {

        return ApiResponse.<List<Course>>builder()
                .result(courseService.findAll())
                .build();
    }
    @GetMapping("/newest")
    ApiResponse<List<Course>> getNewCourses() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.findNewCourse())
                .build();
    }

    @PutMapping("/{course-id}")
    public ApiResponse<Course> update(@RequestBody @Valid CourseRequest courseRequest,
                                      @PathVariable("course-id") String courseId){
        return ApiResponse.<Course>builder()
                .result(courseService.update(courseId,courseRequest))
                .build();
    }




}
