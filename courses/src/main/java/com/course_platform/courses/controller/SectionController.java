package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.SectionRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.dto.response.Section;
import com.course_platform.courses.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    @PostMapping
    public ApiResponse<Section> create(@RequestBody @Valid SectionRequest sectionRequest){
        return ApiResponse.<Section>builder()
                .result(sectionService.create(sectionRequest))
                .build();
    }
    @GetMapping
    public  ApiResponse<List<Section>> getSections(){
        return ApiResponse.<List<Section>>builder()
                .result(sectionService.findAll())
                .build();
    }
    @GetMapping("/{course-code}/course")
    public  ApiResponse<List<Section>> getSectionByCourse(@PathVariable("course-code") String course){
        return ApiResponse.<List<Section>>builder()
                .result(sectionService.findByCourseCode(course))
                .build();
    }
    @PutMapping("/{section-id}")
    public ApiResponse<Section> update(@RequestBody @Valid SectionRequest sectionRequest,
                                       @PathVariable("section-id") String sectionId){
        return ApiResponse.<Section>builder()
                .result(sectionService.update(sectionId,sectionRequest))
                .build();
    }

}
