package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Comment;
import com.course_platform.courses.dto.response.Review;
import com.course_platform.courses.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ApiResponse<Comment> create(@RequestBody @Valid CommentRequest commentRequest){
        return ApiResponse.<Comment>builder()
                .result(commentService.create(commentRequest))
                .build();

    }
    @GetMapping("/{lesson-id}/lesson")
    public ApiResponse<List<Comment>> getByLessonId(@PathVariable("lesson-id") String lessonId){
        return ApiResponse.<List<Comment>>builder()
                .result(commentService.findByLessonId(lessonId))
                .build();
    }
    @PutMapping("/{comment-id}")
    public ApiResponse<Comment> updateComment(@PathVariable("comment-id") String id, @RequestBody CommentUpdateRequest commentUpdateRequest){
        return ApiResponse.<Comment>builder()
                .result(commentService.update(id, commentUpdateRequest))
                .build();
    }
}
