package com.course_platform.courses.repository;

import com.course_platform.courses.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteEntity,String> {
    List<NoteEntity> findByLessonIdAndUserId(String id, String id1);

    @Modifying
    @Query("UPDATE NoteEntity n SET n.note = ?2 WHERE n.id = ?1 ")
    NoteEntity updateNote(String id, String note);
}
