package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.dto.response.Note;
import com.course_platform.courses.service.NoteService;
import com.course_platform.courses.service.NoteServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestConfig.class)
public class NoteControllerTest {
    @MockBean
    private NoteService noteService;
    @Autowired
    private MockMvc mockMvc;
    private NoteRequest noteRequest;
    private Note note;
    private String accessToken;
    private HttpHeaders headers;
    @Autowired
    private AuthRest authRest;
    private ObjectMapper objectMapper;
    @Value("${api.prefix}/notes")
    private String api;
    @BeforeEach
    void initData(){
        objectMapper = new ObjectMapper();
        accessToken = authRest.login();
        headers  = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);
        noteRequest = NoteRequest.builder()
                .lessonId("18ce4226-55b9-4fa0-9894-b32a29c665b9")
                .note("su khac biet giua var,let,const la pham vi truy cap cua bien con su khac nhau giua let va const la const giong nhu hang so chi khai bao 1 lan duy nhat khong the gan lai")
                .build();
        note = Note.builder()
                .id("77a45059-448c-4947-983d-100fcc4807cc")
                .note("su khac biet giua var,let,const la pham vi truy cap cua bien con su khac nhau giua let va const la const giong nhu hang so chi khai bao 1 lan duy nhat khong the gan lai")
                .build();
    }
    @Test
    void createNote_validRequest_success() throws Exception{
        // GIVEN
        String content = objectMapper.writeValueAsString(noteRequest);
        when(noteService.create(any())).thenReturn(note);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("77a45059-448c-4947-983d-100fcc4807cc"));
    }
    @Test
    void createNote_invalidLesson_fail() throws Exception{
        // GIVEN
        noteRequest.setLessonId("");
        String content = objectMapper.writeValueAsString(noteRequest);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Lesson must be not empty"));;
    }
    @Test
    void createNote_invalidNote_fail() throws Exception{
        // GIVEN
        noteRequest.setNote("");
        String content = objectMapper.writeValueAsString(noteRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Note must be not empty"));;
    }
    @Test
    void getNoteByLess_validRequest_success() throws Exception{
        // GIVEN
        String lessonId = "18ce4226-55b9-4fa0-9894-b32a29c665b9";

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/{lesson-id}/lesson" ,lessonId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("77a45059-448c-4947-983d-100fcc4807cc"));;
    }
    @Test
    void updateNote_invalidNote_fail() throws Exception{
        // GIVEN
        String noteId = "77a45059-448c-4947-983d-100fcc4807cc";
        noteRequest.setNote("");
        String content = objectMapper.writeValueAsString(noteRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api + "/{note-id}",noteId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Note must be not empty"));;
    }
    @Test
    void updateNote_invalidParam_fail() throws Exception{
        // GIVEN
        String noteId = "";
        noteRequest.setNote("");
        String content = objectMapper.writeValueAsString(noteRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api + "/{note-id}",noteId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    @Test
    void updateNote_validRequest_fail() throws Exception{
        // GIVEN
        String noteId = "77a45059-448c-4947-983d-100fcc4807cc";
        noteRequest.setNote("edit ghi chu");
        String content = objectMapper.writeValueAsString(noteRequest);
        when(noteService.update(anyString(),any())).thenReturn(note);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api + "/{note-id}",noteId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("77a45059-448c-4947-983d-100fcc4807cc"));;;

    }
    @Test
    void deleteNote_validRequest_success() throws Exception{
        // GIVEN
        String noteId = "77a45059-448c-4947-983d-100fcc4807cc";

        doNothing().when(noteService).delete(anyString());
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.delete(api + "/{note-id}",noteId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));

    }
    @Test
    void deleteNote_invalidParam_success() throws Exception{
        // GIVEN
        String noteId = "";

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.delete(api + "/{note-id}",noteId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }
}
