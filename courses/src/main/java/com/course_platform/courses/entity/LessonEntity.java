package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lesson")
@Getter
@Setter
public class LessonEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String title;
    @Column
    private String code;
    @Column(unique = true)
    private String video;
    @Column
    private int position;
    @ManyToOne (fetch = FetchType.LAZY)
    private CourseEntity course;
    @ManyToOne (fetch = FetchType.LAZY)
    private SectionEntity section;
    @Column
    private boolean isDeleted;

}
