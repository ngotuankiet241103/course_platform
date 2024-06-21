package com.course_platform.courses.repository;

import com.course_platform.courses.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity,String> {
    List<ReviewEntity> findByCourseId(String courseId);
}
