package com.course_platform.courses.service;

import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.UserEntity;

public interface LessonUserService {
    void create(CourseEntity course, String userId);
}
