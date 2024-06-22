package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequest {
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
}
