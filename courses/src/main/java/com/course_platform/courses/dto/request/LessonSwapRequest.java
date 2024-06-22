package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonSwapRequest {
    @NotEmpty(message = "LESSON_INVALID")
    private String lessonId;
    @NotEmpty(message = "SECTION_SOURCE_INVALID")
    private String srcSectionId;
    @NotEmpty(message = "SECTION_DES_INVALID")
    private String desSectionId;
    @Min(value = 1, message = "POSITION_INVALID")
    private int toPosition;
    @NotEmpty(message = "COURSE_INVALID")
    private String courseId;
}
