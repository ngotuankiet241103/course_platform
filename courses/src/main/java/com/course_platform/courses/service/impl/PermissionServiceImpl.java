//package com.course_platform.courses.service.impl;
//
//import com.course_platform.courses.dto.request.PermissionRequest;
//import com.course_platform.courses.dto.response.Persmisson;
//import com.course_platform.courses.entity.PermissionEntity;
//import com.course_platform.courses.mapper.PermissionMapper;
//import com.course_platform.courses.repository.PermissionRepository;
//import com.course_platform.courses.service.PermissionService;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//
//@RequiredArgsConstructor
//public class PermissionServiceImpl implements PermissionService {
//    private final PermissionRepository permissionRepository;
//    private final PermissionMapper permissionMapper;
//
//    @Override
//    public Persmisson create(PermissionRequest permissionRequest) {
//        PermissionEntity permissionEntity = permissionMapper.toPermissionEntity(permissionRequest);
//        return permissionMapper.toPermission(permissionRepository.save(permissionEntity));
//    }
//
//    @Override
//    public List<Persmisson> findAll() {
//        return mappingList(permissionRepository.findAll());
//    }
//    private List<Persmisson> mappingList(List<PermissionEntity> permissions){
//        return permissions.stream()
//                .map(permissionMapper::toPermission)
//                .toList();
//    }
//}
