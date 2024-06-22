package com.course_platform.courses.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    private String id;
    private String video;
    private String title;
    private int position;
    private String code;
    private boolean isBlock;
    private boolean isCompleted;
}
