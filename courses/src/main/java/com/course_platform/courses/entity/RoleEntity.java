package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;
import java.util.Set;


@Table(name = "role")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    @Id
    private String name;
    @Column
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PermissionEntity> permissions;


}
