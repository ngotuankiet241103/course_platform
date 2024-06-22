package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {
    @NotEmpty(message = "LESSON_INVALID")
    private String lessonId;
    @NotEmpty(message = "NOTE_INVALID")
    private String note;

}
