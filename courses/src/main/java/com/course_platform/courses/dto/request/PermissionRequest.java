package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequest {
    @NotEmpty(message = "NAME_INVALID")
    private String name;
    @NotEmpty(message = "DESCRIPTION_INVALID")
    private String description;
}
