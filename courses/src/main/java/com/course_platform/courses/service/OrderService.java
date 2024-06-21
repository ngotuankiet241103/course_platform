package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.OrderRequest;

public interface OrderService {
    String create(OrderRequest orderRequest);
}
