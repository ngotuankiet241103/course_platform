package com.course_platform.courses.repository;

import com.course_platform.courses.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<CourseEntity,String> {


    Page<CourseEntity> findByCategoryCode(Pageable pageable,String code);
}
