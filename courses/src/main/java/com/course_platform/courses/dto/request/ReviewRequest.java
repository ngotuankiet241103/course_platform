package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    @NotEmpty(message = "REVIEW_INVALID")
    private String review;
    @Min(value = 1,message = "RATE_MIN")
    @Max(value = 5,message = "RATE_MAX")
    private int starRate;
    @NotEmpty(message = "COURSE_INVALID")
    private String courseId;
}
