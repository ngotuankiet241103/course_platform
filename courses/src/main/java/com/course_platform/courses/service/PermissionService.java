package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.PermissionRequest;
import com.course_platform.courses.dto.response.Persmisson;

import java.util.List;

public interface PermissionService {
    Persmisson create(PermissionRequest permissionRequest);

    List<Persmisson> findAll();
}
