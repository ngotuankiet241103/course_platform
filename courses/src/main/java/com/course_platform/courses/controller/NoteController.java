package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Note;
import com.course_platform.courses.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    @PostMapping
    public ApiResponse<Note> create(@RequestBody @Valid NoteRequest noteRequest){
        return ApiResponse.<Note>builder()
                .result(noteService.create(noteRequest))
                .build();
    }
    @GetMapping("/{lesson-id}/lesson")
    public ApiResponse<List<Note>> getByLessonId(@PathVariable("lesson-id") String id){
        return ApiResponse.<List<Note>>builder()
                .result(noteService.findByLessonId(id))
                .build();
    }
    @PutMapping("/{note-id}")
    public ApiResponse<Note> update(@RequestBody @Valid NoteRequest noteRequest,
                                    @PathVariable("note-id") String noteId){
        return ApiResponse.<Note>builder()
                .result(noteService.update(noteId,noteRequest))
                .build();
    }
    @DeleteMapping("/{note-id}")
    public ApiResponse<String> update(@PathVariable("note-id") String noteId){
        return ApiResponse.<String>builder()
                .result(noteService.delete(noteId))
                .build();
    }

}
