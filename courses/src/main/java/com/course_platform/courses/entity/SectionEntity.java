package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "section")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;
    @Column
    private String title;
    @Column
    private int position;
    @ManyToOne(fetch =  FetchType.LAZY)
    private CourseEntity course;
    @OneToMany(mappedBy = "section")
    private List<LessonEntity> lessons;
}
