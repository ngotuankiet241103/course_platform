package com.course_platform.courses.repository;

import com.course_platform.courses.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<LessonEntity,String> {
    Optional<LessonEntity> findByCode(String lessonCode);
    @Modifying
    @Query("UPDATE LessonEntity c SET c.position = c.position + 1 WHERE c.course.id = ?1 AND c.position > ?2 ")
    void updatePositionLesson(String courseId, int total);
    int countByCourseId(String id);

    int countBySectionIdAndCourseId(String sectionId,String courseId);
    @Query("SELECT l FROM  LessonEntity l WHERE  l.course.id = ?1 AND l.section.id = ?2 ORDER BY l.position DESC LIMIT 1 OFFSET 0")
    Optional<LessonEntity> findLastBySectionIdAndCourseId(String id, String id1);

    List<LessonEntity> findByCourseId(String id);

    List<LessonEntity> findByCourseIdOrderByPosition(String id);

    @Query("SELECT l FROM LessonEntity l WHERE l.course.id = ?1 AND l.position = ?2")
    LessonEntity findNextLesson(String courseId,int i);
    @Modifying
    @Query("UPDATE LessonEntity c SET c.position = c.position + 1 WHERE c.course.id = ?1 AND c.position < ?2 AND c.position >= ?3  AND c.id != ?4 ")
    void updatePositionMoveUp(String courseId, int fromPosition, int toPosition, String id);
    @Modifying
    @Query("UPDATE LessonEntity c SET c.position = c.position - 1 WHERE c.course.id = ?1 AND c.position > ?2 AND c.position <= ?3 AND c.id != ?4 ")
    void updatePositionMoveDown(String courseId, int fromPosition, int toPosition, String id);
}
