package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.RoleRequest;
import com.course_platform.courses.dto.response.Role;
import com.course_platform.courses.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    RoleEntity toRoleEntity(RoleRequest roleRequest);
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleEntity roleEntity);

}
