package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.RoleRequest;
import com.course_platform.courses.dto.request.RoleUpdate;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Role;
import com.course_platform.courses.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ApiResponse<Role> create(@RequestBody @Valid RoleRequest roleRequest){
        return ApiResponse.<Role>builder()
                .result(roleService.create(roleRequest))
                .build();
    }
    @PutMapping("/{role-name}")
    public ApiResponse<Role> updatePermission(@RequestBody RoleUpdate roleRequest,
                                              @PathVariable("role-name") String roleName){
        return ApiResponse.<Role>builder()
                .result(roleService.updatePermissions(roleRequest,roleName))
                .build();
    }
    @GetMapping
    public ApiResponse<List<Role>> getAll(){
        return ApiResponse.<List<Role>>builder()
                .result(roleService.findAll())
                .build();

    }
}
