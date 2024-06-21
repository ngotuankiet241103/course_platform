package com.course_platform.courses.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonSwapRequest {
    private String lessonId;
    private String srcSectionId;
    private String desSectionId;
    private int toPosition;
    private String courseId;
}
