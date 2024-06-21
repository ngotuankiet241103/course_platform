package com.course_platform.courses.dto.request;

import com.course_platform.courses.entity.CourseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionRequest {
    @NotEmpty(message = "TITLE_INVALID")
    private String title;

    private String courseId;
}
