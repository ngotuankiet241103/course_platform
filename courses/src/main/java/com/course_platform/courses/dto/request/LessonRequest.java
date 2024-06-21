package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonRequest {
    @NotEmpty(message = "TITLE_INVALID")
    private String title;
    private MultipartFile file;
    @NotEmpty(message = "COURSE_INVALID")
    private String courseId;
    @NotEmpty(message = "SECTION_INVALID")
    private String sectionId;


}
