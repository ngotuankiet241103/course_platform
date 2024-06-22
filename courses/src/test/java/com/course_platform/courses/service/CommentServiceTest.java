package com.course_platform.courses.service;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.entity.CommentEntity;
import com.course_platform.courses.entity.LessonEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.repository.CommentRepository;
import com.course_platform.courses.repository.LessonRepository;
import com.course_platform.courses.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Import(RestConfig.class)
public class CommentServiceTest {
    @MockBean
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtDecoder jwtDecoder;
    @MockBean
    private LessonRepository lessonRepository;
    @MockBean
    private UserRepository userRepository;
    private CommentRequest commentRequest;
    private CommentEntity commentEntity;
    private CommentUpdateRequest commentUpdateRequest;
    private UserPrincipal userPrincipal;
    private LessonEntity lessonEntity;
    private Authentication authentication;

    @Autowired
    private AuthRest authRest;
    private String accessToken;
    @BeforeEach
    void initData(){
        accessToken = authRest.login();
        commentRequest = CommentRequest.builder()
                .content("bai hoc rat bo ich")
                .rootId(null)
                .parentId(null)
                .lessonId("18ce4226-55b9-4fa0-9894-b32a29c665b9")
                .build();
        commentEntity = CommentEntity.builder()
                .id("07e465b6-6117-4164-a987-d59ed31c7737")
                .name("Ngo Kiet")
                .nodeLeft(1)
                .nodeRight(2)
                .content("bai hoc rat bo ich")
                .rootId("e0e5eba9-a8cd-46fb-9753-b4e9c5d2f9ca")
                .parentId(null)
                .build();
        userPrincipal = new UserPrincipal();
        userPrincipal.setName("Ngo Kiet");
        lessonEntity = LessonEntity.builder()
                .id("18ce4226-55b9-4fa0-9894-b32a29c665b9")
                .build();

        commentUpdateRequest = CommentUpdateRequest.builder()
                .content("bai hoc rat hay")
                .build();
        System.out.println(accessToken);

        authentication = new JwtAuthenticationToken(jwtDecoder.decode(accessToken));
        SecurityContextHolder.getContext().setAuthentication(authentication);


    }
    @Test
    void createComment_validRequest_success(){
        // GIVEN
        when(lessonRepository.findById(anyString())).thenReturn(Optional.ofNullable(lessonEntity));
        when(commentRepository.save(any())).thenReturn(commentEntity);
        when(userRepository.findById(anyString())).thenReturn(null);
        // WHEN
        var response = commentService.create(commentRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("07e465b6-6117-4164-a987-d59ed31c7737");
    }
    @Test
    void createComment_invalidLesson_fail(){
        // GIVEN
        commentRequest.setLessonId("");
//        when(converter.convert(any())).thenReturn(userPrincipal);

        // WHEN
        var exception = assertThrows(CustomRuntimeException.class,() -> commentService.create(commentRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Lesson not found");
    }
    @Test
    void updateComment_validRequest_success(){
        String commentId = "07e465b6-6117-4164-a987-d59ed31c7737";
        // GIVEN
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(commentEntity));
        when(commentRepository.updateComment(anyString(),anyString())).thenReturn(commentEntity);
        when(userRepository.findById(anyString())).thenReturn(null);
        // WHEN
        var response = commentService.update(commentId,commentUpdateRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("07e465b6-6117-4164-a987-d59ed31c7737");
        assertThat(response.getContent()).isEqualTo("bai hoc rat hay");
    }
    @Test
    void updateComment_invalidComment_fail(){
        String commentId = "07e465b6-6117-4164-a987-d59ed31c7737a";
        // GIVEN

        // WHEN
        var exception = assertThrows(CustomRuntimeException.class,() -> commentService.update(commentId,commentUpdateRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Comment not found");
    }
    @Test
    void getCommentsByLesson_validRequest_success(){
        String lessonId = "18ce4226-55b9-4fa0-9894-b32a29c665b9";
        // GIVEN
        when(commentRepository.findByLessonId(anyString())).thenReturn(new ArrayList<>(List.of(commentEntity)));
        // WHEN
        var response = commentService.findByLessonId(lessonId);
        // THEN
        assertThat(response.stream().anyMatch(comment -> comment.getId().equals("07e465b6-6117-4164-a987-d59ed31c7737"))).isEqualTo(true);

    }



}
