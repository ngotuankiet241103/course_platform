package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
    private String parentId;
    private String rootId;
    @NotEmpty(message = "LESSON_INVALID")
    private String lessonId;
}
