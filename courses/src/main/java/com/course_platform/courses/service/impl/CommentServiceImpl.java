package com.course_platform.courses.service.impl;

import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.dto.response.Comment;
import com.course_platform.courses.dto.response.UserInfo;
import com.course_platform.courses.entity.CommentEntity;
import com.course_platform.courses.entity.LessonEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.CommentMapper;
import com.course_platform.courses.mapper.UserMapper;
import com.course_platform.courses.repository.CommentRepository;
import com.course_platform.courses.repository.LessonRepository;
import com.course_platform.courses.repository.UserRepository;
import com.course_platform.courses.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Converter<JwtAuthenticationToken, UserPrincipal> converter;
    @Override
    public Comment mappingOne(CommentEntity commentEntity) {
        Comment comment = commentMapper.toComment(commentEntity);

        UserInfo userInfo = UserInfo.builder()
                .fullName(commentEntity.getName())
                .image(String.valueOf(userRepository.findById(commentEntity.getUserId()).orElse(null)))
                .build();
        comment.setUserInfo(userInfo);
        return comment;
    }

    @Override
    public List<Comment> mappingList(List<CommentEntity> e) {
        return e.stream().map(this::mappingOne).toList();
    }
    @Transactional
    @Override
    public Comment create(CommentRequest commentRequest) {
       CommentEntity comment = createCommentEntity(commentRequest);
        if(comment.getRootId() != null && !comment.getRootId().isEmpty()) {
            CommentEntity commentParent = commentRepository.findById(comment.getParentId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COMMENT_NOT_FOUND));
            comment.setNodeLeft(commentParent.getNodeLeft() + 1);
            comment.setNodeRight(commentParent.getNodeLeft() + 2);

            updateNodeComment(commentParent);
        }
        else{
            String rootId = UUID.randomUUID().toString();
            comment.setNodeLeft(1);
            comment.setNodeRight(2);
            comment.setRootId(rootId);
        }
        return mappingOne(this.commentRepository.save(comment));
    }

    @Override
    public List<Comment> findByLessonId(String lessonId) {
        return mappingList(this.commentRepository.findByLessonId(lessonId));
    }

    @Override
    public Comment update(String id, CommentUpdateRequest commentUpdateRequest) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COMMENT_NOT_FOUND));
        commentEntity.setContent(commentUpdateRequest.getContent());
        return mappingOne(commentRepository.updateComment(commentEntity.getId(),commentEntity.getContent()));
    }

    private CommentEntity createCommentEntity(CommentRequest commentRequest){
        CommentEntity comment = commentMapper.toCommentEntity(commentRequest);
        UserPrincipal user = converter.convert((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        LessonEntity lesson = lessonRepository.findById(commentRequest.getLessonId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND));
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setUserId(userId);
        comment.setLesson(lesson);
        comment.setName(user.getName());
        return  comment;
    }
    @Async
    private void updateNodeComment(CommentEntity commentEntity){
        this.commentRepository.updateNodeLeft(commentEntity.getRootId(),commentEntity.getNodeLeft());
        this.commentRepository.updateNodeRight(commentEntity.getRootId(),commentEntity.getNodeLeft());


    }
}
