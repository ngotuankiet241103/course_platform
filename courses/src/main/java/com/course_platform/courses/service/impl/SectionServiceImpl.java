package com.course_platform.courses.service.impl;

import com.course_platform.courses.dto.request.SectionRequest;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.dto.response.Section;
import com.course_platform.courses.entity.*;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.LessonMapper;
import com.course_platform.courses.mapper.SectionMapper;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.LessonUserRepository;
import com.course_platform.courses.repository.SectionRepository;
import com.course_platform.courses.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;
    private final LessonUserRepository lessonUserRepository;
    public Section create(SectionRequest sectionRequest) {
        SectionEntity sectionEntity = sectionMapper.toSectionEntity(sectionRequest);
        CourseEntity courseEntity = courseRepository.findById(sectionRequest.getCourseId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
        long total = sectionRepository.count();
        sectionEntity.setPosition((int) ++total );
        sectionEntity.setCourse(courseEntity);

        return sectionMapper.toSection(sectionRepository.save(sectionEntity));
    }

    @Override
    public Section update(String sectionId, SectionRequest sectionRequest) {
        boolean check = sectionRepository.existsById(sectionId);
        if(!check){
            throw new CustomRuntimeException(ErrorCode.SECTION_NOT_FOUND);
        }
        return mappingOne(sectionRepository.updateTitle(sectionId,sectionRequest.getTitle()));
    }

    @Override
    public List<Section> mappingList(List<SectionEntity> sectionEntity){
        return sectionEntity.stream()
                .map(this::mappingOne)
                .toList();
    }
    @Override
    public Section mappingOne(SectionEntity sectionEntity){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Section results = sectionMapper.toSection(sectionEntity);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("SECTION_EDIT"));
        List<Lesson> lessons = sectionEntity.getLessons().stream()
                .sorted(Comparator.comparingInt(LessonEntity::getPosition))
                .map(lessonMapper::toLesson)
                .peek(lesson -> {
                    if(isAdmin){
                        lesson.setBlock(false);
                    }
                    else{
                        System.out.println("hello");
                        LessonUserId lessonUserId = new LessonUserId(lesson.getId(),authentication.getName());
                        LessonUserEntity lessonUserEntity = lessonUserRepository.findById(lessonUserId)
                                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND));
                        lesson.setBlock(lessonUserEntity.isBlock());
                        lesson.setCompleted(lessonUserEntity.isCompleted());
                    }

                })
                .toList();
        results.setLessons(lessons);
        return  results;
    }

    @Override
    public List<Section> findAll() {
        return mappingList(sectionRepository.findAll());
    }

    @Override
    public List<Section> findByCourseCode(String course) {
        return mappingList(sectionRepository.findByCourseCodeOrderByPosition(course));
    }
}