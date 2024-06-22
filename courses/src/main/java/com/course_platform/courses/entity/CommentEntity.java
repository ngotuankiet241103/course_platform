package com.course_platform.courses.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String userId;
    @ManyToOne (fetch = FetchType.LAZY)
    private LessonEntity lesson;
    @Column
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column
    private String rootId;
    @Column
    private String parentId;
    @Column
    private int nodeLeft;
    @Column
    private int nodeRight;
    @Column
    private boolean isVisible;
}
