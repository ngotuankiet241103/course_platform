package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.AuthenticationRequest;
import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.dto.response.Note;
import com.course_platform.courses.entity.NoteEntity;
import jakarta.validation.Valid;

import java.util.List;

public interface NoteService extends BaseService<NoteEntity, Note> {
    Note create(NoteRequest noteRequest);

    List<Note> findByLessonId(String id);

    Note update(String noteId, @Valid AuthenticationRequest.NoteUpdateRequest noteRequest);

    String delete(String noteId);
}
