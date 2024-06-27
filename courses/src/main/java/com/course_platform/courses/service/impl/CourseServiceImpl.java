package com.course_platform.courses.service.impl;

import com.course_platform.courses.dto.request.CourseRequest;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.dto.response.Pagination;
import com.course_platform.courses.dto.response.Paging;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.LessonEntity;
import com.course_platform.courses.entity.LessonUserEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.CategoryMapper;
import com.course_platform.courses.mapper.CourseMapper;
import com.course_platform.courses.repository.*;
import com.course_platform.courses.service.CourseService;
import com.course_platform.courses.service.FileService;
import com.course_platform.courses.utils.HandleString;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final FileService fileService;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final OrderRepository orderRepository;
    private final LessonRepository lessonRepository;
    private final LessonUserRepository lessonUserRepository;
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
            course.setTitle(courseRequest.getTitle());
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

    @Override
    public List<Course> findNewCourse() {
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable pageable = PageRequest.of(0,2,sort);
        Page<CourseEntity> response =  courseRepository.findAll(pageable);
        return mappingList(response.getContent());
    }
    private Paging<Course> createPaging(Page<CourseEntity> page){
        return Paging.<Course>builder()
                .data(mappingList(page.getContent()))
                .pagination(Pagination.builder()
                        .page(page.getNumber())
                        .totalPage(page.getTotalPages())
                        .totalElements((int)page.getTotalElements())
                        .build())
                .build();
    }

    @Override
    public boolean isFree(String courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
        return course.isFree();
    }

    @Override
    public Paging<Course> findByCategoryCode(Pageable pageable,String code) {
        Page<CourseEntity> page = courseRepository.findByCategoryCode(pageable,code);
        return createPaging(page);
    }

    @Async
    private void updateNameCourse(String fileId,String newName){
        fileService.updateFolder(fileId,newName);
    }

    private CourseEntity createCourseEntity(CourseRequest courseRequest){
        String folderId = fileService.createFolder(courseRequest.getTitle());
        CourseEntity course = courseMapper.toCourseEntity(courseRequest);
        course.setFree(courseRequest.getPrice() == 0);
        course.setId(folderId);
        course.setCode(HandleString.strToCode(courseRequest.getTitle()));
        course.setCategory(categoryRepository.findById(courseRequest.getCategoryId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.CATEGORY_NOT_FOUND)));
        return course;
    }
    @Override
    public Paging<Course> findAll(Pageable pageable) {
        Page<CourseEntity> page = courseRepository.findAll(pageable);
        return createPaging(page);
    }

    @Override
    public Course mappingOne(CourseEntity courseEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Course course = courseMapper.toCourse(courseEntity);
        Map<String, Object> detailCourse = null;
        if(authentication != null){
            boolean check = orderRepository.existsByIdUserIdAndIdCourseIdAndIsCompleted(authentication.getName(),courseEntity.getId(),true);
            if(check){
                detailCourse = new HashMap<>();
                int totalLesson = lessonRepository.countByCourseId(courseEntity.getId());
                int currentLesson = lessonUserRepository
                        .countByCourseIdAndIdUserIdAndIsCompleted(course.getId(),authentication.getName(),true);
                System.out.println(totalLesson);
                System.out.println(currentLesson);
                detailCourse.put("progress", (currentLesson * 100 / totalLesson));
            }
        }
        course.setDetail(detailCourse);
        course.setCategory(categoryMapper.toCategory(courseEntity.getCategory()));
        course.setTotalLearner(orderRepository.countByIdCourseIdAndIsCompleted(courseEntity.getId(),true));

        return course;
    }

    @Override
    public List<Course> mappingList(List<CourseEntity> e) {
        return e.stream()
                .map(this::mappingOne).toList();
    }
}
