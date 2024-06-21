package com.course_platform.courses.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {
    @NotEmpty(message = "TITLE_INVALID")
    private String title;
    @NotEmpty(message = "IMAGE_INVALID")
    private String imageCourse;
    @NotEmpty(message = "DESCRIPTION_INVALID")
    private String description;
    @Min(value = 0,message = "PRICE_INVALID")
    private double price;
    @NotEmpty
    private String categoryId;

}
