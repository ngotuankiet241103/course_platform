package com.course_platform.courses.service.impl;

import com.course_platform.courses.entity.*;
import com.course_platform.courses.repository.LessonRepository;
import com.course_platform.courses.repository.LessonUserRepository;
import com.course_platform.courses.service.LessonUserService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonUserServiceImpl implements LessonUserService {
    private final LessonUserRepository lessonUserRepository;
    private final LessonRepository lessonRepository;
    @Override
    public void create(CourseEntity course, String userId) {
        List<LessonEntity> lessons = lessonRepository.findByCourseIdOrderByPosition(course.getId());
        openLessons(lessons,userId);
    }
    private void openLessons(List<LessonEntity> lessons, String userId){
        if(lessons.size() == 0) return;
        LessonEntity firstLesson = lessons.get(0);
        openLesson(firstLesson,userId,false,false);
        for(int i=1; i < lessons.size(); i++){
            LessonEntity lesson = lessons.get(i);
            openLesson(lesson,userId,true,false);
        }
    }
    private void openLesson(LessonEntity lesson,String userId,boolean isBlock,boolean isCompleted){
        LessonUserId lessonUserId = new LessonUserId(lesson.getId(),userId);
        LessonUserEntity lessonUserEntity = LessonUserEntity.builder()
                .id(lessonUserId)
                .isBlock(isBlock)
                .isCompleted(isCompleted)
                .build();
        lessonUserRepository.save(lessonUserEntity);

    }
}
