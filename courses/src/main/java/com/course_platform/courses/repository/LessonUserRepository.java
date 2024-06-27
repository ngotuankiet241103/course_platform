package com.course_platform.courses.repository;

import com.course_platform.courses.entity.LessonUserEntity;
import com.course_platform.courses.entity.LessonUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LessonUserRepository extends JpaRepository<LessonUserEntity, LessonUserId> {
    @Modifying
    @Query("UPDATE LessonUserEntity l SET l.isCompleted = true WHERE l.id.userId = ?1 AND l.id.lessonId = ?2")
    void updateCompleted(String id, String id1);


    @Modifying
    @Query("UPDATE LessonUserEntity l SET l.isBlock = false WHERE l.id.userId= ?1 AND l.id.lessonId = ?2")
    void updateUnblock(String id, String id1);
    @Query("SELECT l FROM LessonUserEntity l WHERE l.id.userId = ?1 AND l.isCompleted=true ORDER BY l.timeCompleted DESC LIMIT 1 OFFSET 0  ")
    LessonUserEntity findByIdUserIdAndCourseIdNewest(String user, String courseId);

    int countByCourseIdAndIdUserIdAndIsCompleted(String id, String name, boolean b);

    Optional<LessonUserEntity> findByIdAndCourseId(LessonUserId lessonUserId, String courseId);
}
