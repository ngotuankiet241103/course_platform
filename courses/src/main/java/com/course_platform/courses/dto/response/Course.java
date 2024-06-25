package com.course_platform.courses.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    private String id;
    private String code;
    private String title;
    private String imageCourse;
    private String description;
    private double price;
    private Category category;
    private boolean isFree;

}
