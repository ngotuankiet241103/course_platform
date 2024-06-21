package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.PermissionRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Persmisson;
import com.course_platform.courses.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @PostMapping
    public ApiResponse<Persmisson> create(@RequestBody @Valid PermissionRequest permissionRequest){
        return ApiResponse.<Persmisson>builder()
                .result(permissionService.create(permissionRequest))
                .build();
    }
    @GetMapping
    public ApiResponse<List<Persmisson>> getAll(){
        return ApiResponse.<List<Persmisson>>builder()
                .result(permissionService.findAll())
                .build();
    }
}
