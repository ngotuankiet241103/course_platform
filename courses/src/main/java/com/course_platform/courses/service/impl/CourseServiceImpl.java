package com.course_platform.courses.service.impl;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.CategoryMapper;
import com.course_platform.courses.mapper.CourseMapper;
import com.course_platform.courses.repository.CategoryRepository;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.service.CourseService;
import com.course_platform.courses.service.FileService;
import com.course_platform.courses.utils.HandleString;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final FileService fileService;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public Course create(CourseRequest courseRequest) {
        System.out.println("hello");
        CourseEntity course = createCourseEntity(courseRequest);

        return courseMapper.toCourse(courseRepository.save(course));
    }

    @Override
    public Course update(String courseId, CourseRequest courseRequest) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
        if(!course.getTitle().equals(courseRequest.getTitle())){
            updateNameCourse(course.getId(),courseRequest.getTitle());
            course.setTitle(course.getTitle());
            course.setCode(HandleString.strToCode(courseRequest.getTitle()));

        }
        if(!course.getCategory().getId().equals(courseRequest.getCategoryId())){
            course.setCategory(categoryRepository.findById(courseRequest.getCategoryId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.CATEGORY_NOT_FOUND)));
        }
        course.setImageCourse(courseRequest.getImageCourse());
        course.setDescription(courseRequest.getDescription());
        course.setPrice(courseRequest.getPrice());
        return mappingOne(courseRepository.save(course));
    }
    @Async
    private void updateNameCourse(String fileId,String newName){
        fileService.updateFolder(fileId,newName);
    }

    private CourseEntity createCourseEntity(CourseRequest courseRequest){
        String folderId = fileService.createFolder(courseRequest.getTitle());

        CourseEntity course = courseMapper.toCourseEntity(courseRequest);
        course.setId(folderId);
        course.setCode(HandleString.strToCode(courseRequest.getTitle()));
        course.setCategory(categoryRepository.findById(courseRequest.getCategoryId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.CATEGORY_NOT_FOUND)));
        return course;
    }
    @Override
    public List<Course> findAll() {
        return mappingList(courseRepository.findAll());
    }

    @Override
    public Course mappingOne(CourseEntity courseEntity) {
        Course course = courseMapper.toCourse(courseEntity);
        course.setCategory(categoryMapper.toCategory(courseEntity.getCategory()));
        return course;
    }

    @Override
    public List<Course> mappingList(List<CourseEntity> e) {
        return e.stream()
                .map(this::mappingOne).toList();
    }
}
