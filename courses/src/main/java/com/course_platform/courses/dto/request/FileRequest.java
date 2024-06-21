package com.course_platform.courses.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileRequest {
    private MultipartFile file;
    private String folderParentId;
}
