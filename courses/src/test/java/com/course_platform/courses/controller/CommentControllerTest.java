package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.dto.request.NoteRequest;
import com.course_platform.courses.dto.response.Comment;
import com.course_platform.courses.dto.response.Note;
import com.course_platform.courses.entity.CommentEntity;
import com.course_platform.courses.service.CommentService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestConfig.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    private CommentRequest commentRequest;
    private Comment comment;
    private String accessToken;
    private HttpHeaders headers;
    @Autowired
    private AuthRest authRest;
    private ObjectMapper objectMapper;
    private CommentUpdateRequest commentUpdateRequest;
    @Value("${api.prefix}/comments")
    private String api;

    @BeforeEach
    void initData(){
        objectMapper = new ObjectMapper();
        accessToken = authRest.login();
        headers  = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);
        commentRequest = CommentRequest.builder()
                .lessonId("c8bb08a4-d240-4d55-b86b-e3b8a14a65d7")
                .rootId("")
                .parentId("")
                .content("Comment 1")
                .build();
        comment = Comment.builder()
                .id("07e465b6-6117-4164-a987-d59ed31c7737")
                .nodeLeft(1)
                .nodeRight(2)
                .content("bai hoc rat bo ich")
                .rootId("e0e5eba9-a8cd-46fb-9753-b4e9c5d2f9ca")
                .parentId(null)
                .build();
        commentUpdateRequest = CommentUpdateRequest.builder()
                .content("Rat bo ich")
                .build();
    }
    @Test
    void createComment_validRequest_success() throws Exception{
        // GIVEN
        String content = objectMapper.writeValueAsString(commentRequest);
        when(commentService.create(any())).thenReturn(comment);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("07e465b6-6117-4164-a987-d59ed31c7737"));;
    }
    @Test
    void createComment_invalidLesson_fail() throws Exception{
        // GIVEN
        commentRequest.setLessonId("");
        String content = objectMapper.writeValueAsString(commentRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Lesson must be not empty"));;
    }
    @Test
    void createComment_invalidContent_fail() throws Exception{
        // GIVEN
        commentRequest.setContent("");
        String content = objectMapper.writeValueAsString(commentRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Content must be not empty"));;
    }
    @Test
    void updateComment_invalidContent_fail() throws Exception{
        // GIVEN
        String commentId = "07e465b6-6117-4164-a987-d59ed31c7737";
        commentUpdateRequest.setContent("");
        String content = objectMapper.writeValueAsString(commentUpdateRequest);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/{comment-id}",commentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Content must be not empty"));;
    }
    @Test
    void updateComment_validRequest_success() throws Exception{
        // GIVEN
        comment.setContent(commentUpdateRequest.getContent());
        String commentId = "07e465b6-6117-4164-a987-d59ed31c7737";
        String content = objectMapper.writeValueAsString(commentUpdateRequest);
        when(commentService.update(anyString(),any())).thenReturn(comment);
        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/{comment-id}",commentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.content").value("Rat bo ich"));;
    }
    @Test
    void getCommentsByLesson_validRequest_success() throws Exception{
        // GIVEN
        String lessonId = "18ce4226-55b9-4fa0-9894-b32a29c665b9";
        when(commentService.findByLessonId(anyString())).thenReturn(new ArrayList<>(List.of(comment)));

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.get(api + "/{lesson-id}/lesson",lessonId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));

    }
}
