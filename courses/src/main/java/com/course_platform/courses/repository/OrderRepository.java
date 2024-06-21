package com.course_platform.courses.repository;

import com.course_platform.courses.entity.OrderEntity;
import com.course_platform.courses.entity.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, OrderId> {
    boolean existsByIdUserIdAndIdCourseId(String id, String id1);

    List<OrderEntity> findByIdCourseId(String courseId);
}
