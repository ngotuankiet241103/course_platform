package com.course_platform.courses.repository;

import com.course_platform.courses.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,String> {
    @Modifying
    @Query("UPDATE CommentEntity c SET c.nodeLeft = c.nodeLeft + 2 WHERE c.rootId = ?1 AND  c.nodeLeft > ?2")
    void updateNodeLeft(String rootId, int nodeLeft);
    @Modifying
    @Query("UPDATE CommentEntity c SET c.nodeRight = c.nodeRight + 2 WHERE c.rootId = ?1 AND  c.nodeRight > ?2")
    void updateNodeRight(String rootId, int nodeLeft);

    List<CommentEntity> findByLessonId(String lessonId);
}
