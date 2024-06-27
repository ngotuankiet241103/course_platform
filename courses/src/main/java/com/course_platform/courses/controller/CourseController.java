package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.dto.response.Paging;
import com.course_platform.courses.service.CourseService;
import com.course_platform.courses.utils.OrderStatus;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    ApiResponse<Paging<Course>> getCourses(@RequestParam(value = "page",defaultValue = "0") int page,
                                           @RequestParam(value = "size",defaultValue = "10") int size,
                                           @RequestParam(value = "sortName",defaultValue = "title" ) String sortName,
                                           @RequestParam(value = "sortBy",defaultValue = "ASC") String sortBy) {
        Sort sort =  Sort.by(Sort.Direction.fromString(sortBy),sortName);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ApiResponse.<Paging<Course>>builder()
                .result(courseService.findAll(pageable))
                .build();
    }
    @GetMapping("/{category-code}/category")
    ApiResponse<Paging<Course>> getCoursesByCategory(@PathVariable("category-code") String categoryCode,
                                           @RequestParam(value = "page",defaultValue = "0") int page,
                                           @RequestParam(value = "size",defaultValue = "10") int size,
                                           @RequestParam(value = "sortName",defaultValue = "title" ) String sortName,
                                           @RequestParam(value = "sortBy",defaultValue = "ASC") String sortBy) {
        Sort sort =  Sort.by(Sort.Direction.fromString(sortBy),sortName);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ApiResponse.<Paging<Course>>builder()
                .result(courseService.findByCategoryCode(pageable,categoryCode))
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
