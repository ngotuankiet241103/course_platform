package com.course_platform.courses.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RoleUpdate {
    List<String> permissions;
}
