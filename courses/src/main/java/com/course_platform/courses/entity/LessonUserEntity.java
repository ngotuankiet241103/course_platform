package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "lesson_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonUserEntity {
    @EmbeddedId
    private LessonUserId id;
    @Column
    private boolean isBlock;
    @Column
    private boolean isCompleted;
    @Column
    private Date timeCompleted;
    @Column
    private String courseId;
}
