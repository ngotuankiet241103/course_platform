package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.ReviewRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Review;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<Review> create(@RequestBody @Valid ReviewRequest reviewRequest){
        return ApiResponse.<Review>builder()
                .result(reviewService.create(reviewRequest))
                .build();

    }
    @GetMapping("/{course-id}/course")
    public ApiResponse<List<Review>> getByCourseId(@PathVariable("course-id") String courseId){
        return ApiResponse.<List<Review>>builder()
                .result(reviewService.findByCourseId(courseId))
                .build();

    }



}
