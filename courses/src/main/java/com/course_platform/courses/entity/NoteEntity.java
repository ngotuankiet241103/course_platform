package com.course_platform.courses.entity;

import com.course_platform.courses.dto.response.Lesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "note")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private LessonEntity lesson;
    @Column
    private String userId;
    @Column
    private String note;

}
