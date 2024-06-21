package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.PermissionRequest;
import com.course_platform.courses.dto.response.Persmisson;
import com.course_platform.courses.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Persmisson toPermission(PermissionEntity permissionEntity);
    PermissionEntity toPermissionEntity(PermissionRequest permissionRequest);
}
