package com.course_platform.courses.service.impl;

import com.course_platform.courses.controller.NoteUpdateRequest;
import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.dto.response.Note;
import com.course_platform.courses.entity.LessonEntity;
import com.course_platform.courses.entity.NoteEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.NoteMapper;
import com.course_platform.courses.repository.LessonRepository;
import com.course_platform.courses.repository.NoteRepository;
import com.course_platform.courses.repository.UserRepository;
import com.course_platform.courses.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final LessonRepository lessonRepository;
    private final NoteMapper noteMapper;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    @Override
    public Note mappingOne(NoteEntity noteEntity) {
        return noteMapper.toNote(noteEntity);
    }

    @Override
    public List<Note> mappingList(List<NoteEntity> e) {
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }

    @Override
    public Note create(NoteRequest noteRequest) {

        NoteEntity note = createNoteEntity(noteRequest);
        return mappingOne(noteRepository.save(note));
    }

    @Override
    public List<Note> findByLessonId(String id) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return mappingList(noteRepository.findByLessonIdAndUserId(id,userId));
    }

    @Override
    public Note update(String noteId, @Valid NoteUpdateRequest noteRequest) {
        NoteEntity note = noteRepository.findById(noteId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOTE_NOT_FOUND));

        return mappingOne(noteRepository.updateNote(note.getId(),noteRequest.getNote()));
    }

    @Override
    public String delete(String noteId) {
        noteRepository.deleteById(noteId);
        return "Delete note success";
    }

    private NoteEntity createNoteEntity(NoteRequest noteRequest){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        LessonEntity lesson = lessonRepository.findById(noteRequest.getLessonId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND));

        NoteEntity noteEntity = noteMapper.toNoteEntity(noteRequest);
        noteEntity.setLesson(lesson);
        noteEntity.setUserId(userId);
        return noteEntity;
    }
}
