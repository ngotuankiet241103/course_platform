package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.RoleRequest;
import com.course_platform.courses.dto.request.RoleUpdate;
import com.course_platform.courses.dto.response.Role;
import com.course_platform.courses.entity.RoleEntity;

import java.util.List;

public interface RoleService  extends BaseService<RoleEntity,Role> {
    Role create(RoleRequest roleRequest);

    List<Role> findAll();

    Role updatePermissions(RoleUpdate roleRequest,String roleName);
}
