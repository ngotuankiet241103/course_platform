//package com.course_platform.courses.service.impl;
//
//import com.course_platform.courses.dto.request.RoleRequest;
//import com.course_platform.courses.dto.request.RoleUpdate;
//import com.course_platform.courses.dto.response.Persmisson;
//import com.course_platform.courses.dto.response.Role;
//import com.course_platform.courses.entity.PermissionEntity;
//import com.course_platform.courses.entity.RoleEntity;
//import com.course_platform.courses.exception.CustomRuntimeException;
//import com.course_platform.courses.exception.ErrorCode;
//import com.course_platform.courses.mapper.PermissionMapper;
//import com.course_platform.courses.mapper.RoleMapper;
//import com.course_platform.courses.repository.PermissionRepository;
//import com.course_platform.courses.repository.RoleRepository;
//import com.course_platform.courses.service.RoleService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//
//@RequiredArgsConstructor
//public class RoleServiceImpl implements RoleService {
//    private final RoleRepository roleRepository;
//    private final PermissionRepository permissionRepository;
//    private final RoleMapper roleMapper;
//    private final PermissionMapper permissionMapper;
//    @PostConstruct
//    private void initRole(){
//        boolean isRoleExists = roleRepository.existsByName("USER");
//        if(isRoleExists) return;
//        RoleEntity role = RoleEntity.builder()
//                .name("USER")
//                .description("role for user")
//                .build();
//        roleRepository.save(role);
//    }
//    @Override
//    public Role create(RoleRequest roleRequest) {
//       RoleEntity role = createRoleEntity(roleRequest);
//
//        return mappingOne(roleRepository.save(role));
//    }
//
//    @Override
//    public List<Role> findAll() {
//        return mappingList(roleRepository.findAll());
//    }
//
//    @Override
//    public Role updatePermissions(RoleUpdate roleRequest,String roleName) {
//        List<PermissionEntity> permissions = findPermissionWithName(roleRequest.getPermissions());
//        RoleEntity role = roleRepository.findByName(roleName)
//                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ROLE_NOT_FOUND));
//        role.setPermissions(new HashSet<>(permissions));
//
//        return roleMapper.toRole(roleRepository.save(role));
//    }
//    @Override
//    public List<Role> mappingList(List<RoleEntity> roles){
//        return roles.stream()
//                .map(this::mappingOne)
//                .toList();
//    }
//    private RoleEntity createRoleEntity(RoleRequest roleRequest){
//        List<PermissionEntity> permissions = findPermissionWithName(roleRequest.getPermissions());
//        RoleEntity role = roleMapper.toRoleEntity(roleRequest);
//        role.setPermissions(new HashSet<>(permissions));
//        return role;
//    }
//    private List<PermissionEntity> findPermissionWithName(List<String> permissions){
//        return permissions.stream()
//                .map(permission -> permissionRepository.findByName(permission)
//                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PERSMISSION_NOT_FOUND)))
//                .toList();
//    }
//    @Override
//    public Role mappingOne(RoleEntity roleEntity){
//        List<Persmisson> persmissons = new ArrayList<>(roleEntity.getPermissions()).stream()
//                .map(permissionMapper::toPermission).toList();
//        Role role = roleMapper.toRole(roleEntity);
//        role.setPermissions(persmissons);
//        return role;
//    }
//
//}
