package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseEntity toCourseEntity(CourseRequest courseRequest);
    @Mapping(target = "category",ignore = true)
    Course toCourse(CourseEntity courseEntity);
}
