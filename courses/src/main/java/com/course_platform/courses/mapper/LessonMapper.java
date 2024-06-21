package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.LessonRequest;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.entity.LessonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toLesson(LessonEntity lessonEntity);
    LessonEntity toLessonEntity(LessonRequest lessonRequest);

}
