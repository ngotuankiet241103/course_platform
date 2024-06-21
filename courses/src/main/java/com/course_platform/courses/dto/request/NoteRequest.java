package com.course_platform.courses.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequest {
    private String lessonId;
    private String note;

}
