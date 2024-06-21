package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @Column
    private String avatar;


}
