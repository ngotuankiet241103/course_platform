package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.response.Comment;
import com.course_platform.courses.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentEntity toCommentEntity(CommentRequest commentRequest);
    Comment toComment(CommentEntity commentEntity);
}
