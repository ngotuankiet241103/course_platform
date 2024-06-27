package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.dto.response.Paging;
import com.course_platform.courses.entity.CourseEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService extends BaseService< CourseEntity,Course> {
    Paging<Course> findAll(Pageable pageable);


    Course create(CourseRequest courseRequest);

    Course update(String courseId, CourseRequest courseRequest);

    List<Course> findNewCourse();

    boolean isFree(String courseId);

    Paging<Course> findByCategoryCode(Pageable pageable, String categoryCode);
}
