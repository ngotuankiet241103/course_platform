package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.LessonRequest;
import com.course_platform.courses.dto.request.LessonSwapRequest;
import com.course_platform.courses.dto.request.LessonUpdateRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    @PostMapping
    public ApiResponse<Lesson> create(@ModelAttribute @Valid LessonRequest lessonRequest){
        return ApiResponse.<Lesson>builder()
                .result( lessonService.create(lessonRequest))
                .build();
    }
    @GetMapping
    public ApiResponse<List<Lesson>> getLessons(){
        return ApiResponse.<List<Lesson>>builder()
                .result(lessonService.findAll())
                .build();
    }
    @GetMapping("/{lesson-code}")
    public ApiResponse<Lesson> getLessonByCode(@PathVariable("lesson-code") String lessonCode){
        return ApiResponse.<Lesson>builder()
                .result(lessonService.findByCode(lessonCode))
                .build();
    }
    @PutMapping("/completed")
    public ApiResponse<Lesson> updateLesson(@RequestBody @Valid LessonUpdateRequest lessonUpdateRequest){
        return ApiResponse.<Lesson>builder()
                .result( lessonService.update(lessonUpdateRequest))
                .build();
    }
    @PutMapping("/swap")
    public ApiResponse<Lesson> updateLesson(@RequestBody LessonSwapRequest lessonSwapRequest){
        return ApiResponse.<Lesson>builder()
                .result( lessonService.swapLesson(lessonSwapRequest))
                .build();
    }
    @PutMapping("/{lesson-id}")
    public ApiResponse<Lesson> update(@ModelAttribute @Valid LessonRequest lessonRequest,
                                      @PathVariable("lesson-id") String lessonId){
        return ApiResponse.<Lesson>builder()
                .result( lessonService.updateInfo(lessonId,lessonRequest))
                .build();
    }
    @DeleteMapping("/{lesson-id}")
    public ApiResponse<Lesson> delete(@PathVariable("lesson-id") String lessonId){

        return ApiResponse.<Lesson>builder()
                .result( lessonService.delete(lessonId))
                .build();
    }
}
