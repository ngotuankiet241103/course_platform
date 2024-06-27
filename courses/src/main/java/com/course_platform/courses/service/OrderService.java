package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.OrderRequest;

public interface OrderService {
    String create(OrderRequest orderRequest, String code);

    void updateStatus(String code, String status);

    String generateUrl(String code, String redirectUrl);

    String delayGenerateUrl(String code, String redirectUrl);
}
