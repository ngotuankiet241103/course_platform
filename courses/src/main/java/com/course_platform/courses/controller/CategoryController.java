package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.CategoryRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Category;
import com.course_platform.courses.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ApiResponse<List<Category>> getAll(){
        return ApiResponse.<List<Category>>builder()
                .result(categoryService.findAll())
                .build();
    }
    @PostMapping
    public ApiResponse<Category> create(@RequestBody @Valid CategoryRequest categoryRequest){
        return ApiResponse.<Category>builder()
                .result(categoryService.create(categoryRequest))
                .build();
    }
    @PutMapping("/{category-id}")
    public ApiResponse<Category> update(@RequestBody @Valid CategoryRequest categoryRequest,
                                        @PathVariable("category-id") String categoryId){
        return ApiResponse.<Category>builder()
                .result(categoryService.update(categoryId,categoryRequest))
                .build();
    }

}
