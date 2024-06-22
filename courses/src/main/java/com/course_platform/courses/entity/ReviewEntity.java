package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;
    @Column
    private int starRate;
    @ManyToOne(fetch = FetchType.LAZY)
    private CourseEntity course;
    @Column
    private String userId;
    @Column
    private String review;
    @Column
    private String name;
}
