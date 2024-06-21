package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.LessonRequest;
import com.course_platform.courses.dto.request.LessonSwapRequest;
import com.course_platform.courses.dto.request.LessonUpdateRequest;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.entity.LessonEntity;

import java.util.List;

public interface LessonService extends BaseService<LessonEntity,Lesson> {
    List<Lesson> findAll();

    Lesson findByCode(String lessonCode);

    Lesson create(LessonRequest lessonRequest);

    Lesson update(LessonUpdateRequest lessonUpdateRequest);

    Lesson swapLesson(LessonSwapRequest lessonSwapRequest);

    Lesson updateInfo(String lessonId, LessonRequest lessonRequest);

    Lesson delete(String lessonId);
}
