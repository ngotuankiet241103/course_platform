package com.course_platform.courses.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class OrderId implements Serializable {
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "user_id")
    private String userId;

    public OrderId() {
    }
}
