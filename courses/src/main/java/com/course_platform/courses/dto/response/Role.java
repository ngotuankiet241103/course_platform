package com.course_platform.courses.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Role {
    private String name;
    private String description;
    private List<Persmisson> permissions;
}
