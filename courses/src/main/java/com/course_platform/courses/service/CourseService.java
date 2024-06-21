package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.entity.CourseEntity;

import java.util.List;

public interface CourseService extends BaseService< CourseEntity,Course> {
    List<Course> findAll();


    Course create(CourseRequest courseRequest);

    Course update(String courseId, CourseRequest courseRequest);
}
