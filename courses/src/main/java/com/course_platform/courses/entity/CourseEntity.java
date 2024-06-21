package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity extends BaseEntity{
    @Id
    private String id;
    @Column
    private String title;
    @Column
    private String code;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String imageCourse;
    @Column
    private double price;
    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

}
