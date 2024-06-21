package com.course_platform.courses.repository;

import com.course_platform.courses.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,String> {
}
