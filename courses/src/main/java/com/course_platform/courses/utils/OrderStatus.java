package com.course_platform.courses.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    COMPLETED("Hoàn tất mua khóa học"),
    PENDING("Đang trong quá trình thanh toán"),
    CANCEL("Đã xảy ra lỗi trong quá trình thanh toán");
    private final String status;
}
