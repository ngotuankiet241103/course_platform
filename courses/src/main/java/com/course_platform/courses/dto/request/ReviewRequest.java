package com.course_platform.courses.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private String review;
    private int starRate;
    private String courseId;
}
