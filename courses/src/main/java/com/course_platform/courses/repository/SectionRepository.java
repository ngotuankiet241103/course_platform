package com.course_platform.courses.repository;

import com.course_platform.courses.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectionRepository extends JpaRepository<SectionEntity,String> {
    List<SectionEntity> findByCourseCode(String course);

    List<SectionEntity> findByCourseCodeOrderByPosition(String course);
    @Modifying
    @Query("UPDATE SectionEntity s SET s.title = ?2 WHERE s.id = ?1")
    SectionEntity updateTitle(String id, String title);
}
