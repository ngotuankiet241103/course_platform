package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.dto.response.Comment;
import com.course_platform.courses.entity.CommentEntity;

import java.util.List;

public interface CommentService extends BaseService<CommentEntity, Comment> {
    Comment create(CommentRequest commentRequest);

    List<Comment> findByLessonId(String lessonId);

    Comment update(String id, CommentUpdateRequest commentUpdateRequest);
}
