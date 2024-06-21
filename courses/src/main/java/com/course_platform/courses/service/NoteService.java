package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.dto.response.Note;
import com.course_platform.courses.entity.NoteEntity;

import java.util.List;

public interface NoteService extends BaseService<NoteEntity, Note> {
    Note create(NoteRequest noteRequest);

    List<Note> findByLessonId(String id);

    Note update(String noteId,NoteRequest noteRequest);

    String delete(String noteId);
}
