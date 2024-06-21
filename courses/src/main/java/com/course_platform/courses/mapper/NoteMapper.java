package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.dto.response.Note;
import com.course_platform.courses.entity.NoteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note toNote(NoteEntity noteEntity);
    NoteEntity toNoteEntity(NoteRequest noteRequest);
}
