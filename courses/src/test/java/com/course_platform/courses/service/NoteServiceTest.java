package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.AuthenticationRequest;
import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.entity.LessonEntity;
import com.course_platform.courses.entity.NoteEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.repository.LessonRepository;
import com.course_platform.courses.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class NoteServiceTest {
    @MockBean
    private NoteRepository noteRepository;
    @MockBean
    private LessonRepository lessonRepository;
    @Autowired
    private NoteService noteService;
    private NoteRequest noteRequest;
    private NoteEntity noteEntity;
    private LessonEntity lessonEntity;
    private Authentication authentication;
    @Value("${test.oauth2.username}")
    private String username;
    @Value("${test.oauth2.password}")
    private String password;
    private AuthenticationRequest.NoteUpdateRequest noteUpdateRequest;
    @BeforeEach
    void initData(){

        noteRequest = NoteRequest.builder()
                .lessonId("18ce4226-55b9-4fa0-9894-b32a29c665b9")
                .note("su khac biet giua var,let,const la pham vi truy cap cua bien con su khac nhau giua let va const la const giong nhu hang so chi khai bao 1 lan duy nhat khong the gan lai")
                .build();
        noteEntity = NoteEntity.builder()
                .id("77a45059-448c-4947-983d-100fcc4807cc")
                .note("su khac biet giua var,let,const la pham vi truy cap cua bien con su khac nhau giua let va const la const giong nhu hang so chi khai bao 1 lan duy nhat khong the gan lai")
                .build();
        lessonEntity = LessonEntity.builder()
                .id("18ce4226-55b9-4fa0-9894-b32a29c665b9")
                .build();
        noteUpdateRequest = AuthenticationRequest.NoteUpdateRequest.builder()
                .note("ghi chu")
                .build();
        authentication = new UsernamePasswordAuthenticationToken(username,password, List.of(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @Test
    void createNote_validRequest_success(){
        // GIVEN
        when(lessonRepository.findById(anyString())).thenReturn(Optional.ofNullable(lessonEntity));
        when(noteRepository.save(any())).thenReturn(noteEntity);
        // WHEN
        var response = noteService.create(noteRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("77a45059-448c-4947-983d-100fcc4807cc");

    }
    @Test
    void createNote_invalidLesson_fail(){
        // GIVEN
        noteRequest.setLessonId(noteRequest.getLessonId() + "a");

        // WHEN
        var exeption = assertThrows(CustomRuntimeException.class,() -> noteService.create(noteRequest));
        // THEN
        assertThat(exeption.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exeption.getErrorCode().getMessage()).isEqualTo("Lesson not found");
    }
    @Test
    void updateNote_invalidNote_fail(){
        // GIVEN
        String noteId = "77a45059-448c-4947-983d-100fcc4807c";


        // WHEN
        var exeption = assertThrows(CustomRuntimeException.class,() -> noteService.update(noteId,noteUpdateRequest));
        // THEN
        assertThat(exeption.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exeption.getErrorCode().getMessage()).isEqualTo("Note not found");
    }
    @Test
    void updateNote_validRequest_success(){
        // GIVEN
        String noteId = "77a45059-448c-4947-983d-100fcc4807cc";
        noteEntity.setNote(noteUpdateRequest.getNote());
        when(noteRepository.findById(noteId)).thenReturn(Optional.ofNullable(noteEntity));
        when(noteRepository.updateNote(anyString(),anyString())).thenReturn(noteEntity);

        // WHEN
        var response = noteService.update(noteId,noteUpdateRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("77a45059-448c-4947-983d-100fcc4807cc");
        assertThat(response.getNote()).isEqualTo("ghi chu");
    }
    @Test
    void getNoteByLesson_validRequest_success(){
        // GIVEN
        String lessonId = "18ce4226-55b9-4fa0-9894-b32a29c665b9";
        when(noteRepository.findByLessonIdAndUserId(anyString(),anyString())).thenReturn(new ArrayList<>(List.of(noteEntity)));
        // WHEN
        var response = noteService.findByLessonId(lessonId);
        // THEN

        assertThat(response.size()).isEqualTo(1);
        assertThat(response.stream().anyMatch(note -> note.getId().equals("77a45059-448c-4947-983d-100fcc4807cc"))).isEqualTo(true);
    }
    @Test
    void deleteNote_validRequest_success(){
        // GIVEN
        String noteId = "77a45059-448c-4947-983d-100fcc4807cc";
        doNothing().when(noteRepository).deleteById(anyString());
        // WHEN
        var response = noteService.delete(noteId);
        // THEN
        assertThat(response).isEqualTo("Delete note success");
    }
}
