package com.course_platform.courses.entity;

import com.course_platform.courses.dto.response.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderEntity extends BaseEntity{
    @Column
    private Date timeOrder;
    @EmbeddedId
    private OrderId id;

}
