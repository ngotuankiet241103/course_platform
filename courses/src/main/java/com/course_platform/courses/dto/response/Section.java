package com.course_platform.courses.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section {
    private String id;
    private String title;
    private List<Lesson> lessons;
}
