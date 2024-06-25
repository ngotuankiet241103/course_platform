package com.course_platform.courses.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Paging {
    COURSE(1,10);
    private int size;
    private int page;
}
