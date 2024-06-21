package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.Set;

@Entity
@Table(name = "permission")
@Getter
@Setter
public class PermissionEntity {
    @Id
    private String name;
    @Column
    private String description;

}
