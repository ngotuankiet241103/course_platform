package com.course_platform.courses.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String content;
    private String parentId;
    private String rootId;

    private String lessonId;
}
