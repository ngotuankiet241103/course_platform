package com.course_platform.courses.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonUpdateRequest {
    private String id;
    private String title;
    private String video;
    private String description;
}
