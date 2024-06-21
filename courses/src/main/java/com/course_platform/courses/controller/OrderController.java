package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.OrderRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ApiResponse<Void> create(@RequestBody @Valid OrderRequest orderRequest){
        return ApiResponse.<Void>builder()
                .message(orderService.create(orderRequest))
                .build();
    }
}
