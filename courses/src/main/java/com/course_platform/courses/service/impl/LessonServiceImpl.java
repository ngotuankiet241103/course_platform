package com.course_platform.courses.service.impl;

import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.request.FileRequest;
import com.course_platform.courses.dto.request.LessonRequest;
import com.course_platform.courses.dto.request.LessonSwapRequest;
import com.course_platform.courses.dto.request.LessonUpdateRequest;
import com.course_platform.courses.dto.response.Lesson;
import com.course_platform.courses.entity.*;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.LessonMapper;
import com.course_platform.courses.repository.*;
import com.course_platform.courses.service.FileService;
import com.course_platform.courses.service.LessonService;
import com.course_platform.courses.utils.HandleString;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final FileService fileService;
    private final LessonMapper lessonMapper;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;
    private final LessonUserRepository lessonUserRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Lesson create(LessonRequest lessonRequest) {
        LessonEntity lesson = createLessonEntity(lessonRequest);
        LessonEntity lessonEntity = lessonRepository.save(lesson);
        openLessonOfCourse(lessonEntity);
        return lessonMapper.toLesson(lessonEntity);
    }
    @Async
    private void openLessonOfCourse(LessonEntity lesson){
        List<OrderEntity> orders = orderRepository.findByIdCourseId(lesson.getCourse().getId());
        List<String> users = orders.stream().map(order -> order.getId().getUserId()).toList();
        users.forEach(user -> {
            LessonUserEntity lessonUserEntity = lessonUserRepository.findByIdUserIdNewest(user);
            LessonUserId id = new LessonUserId(lesson.getId(),user);
            LessonUserEntity lessonUser = LessonUserEntity.builder()
                    .id(id)
                    .isCompleted(false)
                    .build();
            if(lessonUserEntity != null){
                LessonEntity lessonEntity = lessonRepository.findById(lessonUserEntity.getId().getLessonId())
                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND));
                lessonUser.setBlock(lesson.getPosition() > lessonEntity.getPosition());

            }
            else{
                lessonUser.setBlock(true);

            }
            lessonUserRepository.save(lessonUser);


        });

    }
    @Transactional
    @Override
    public Lesson update(LessonUpdateRequest lessonUpdateRequest) {
        LessonEntity lessonEntity = lessonRepository.findById(lessonUpdateRequest.getId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND));
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lessonUserRepository.updateCompleted(userPrincipal.getId(),lessonEntity.getId());
        return openNextLesson(lessonEntity,userPrincipal);
    }
    @Transactional
    @Override
    public Lesson swapLesson(LessonSwapRequest lessonSwapRequest) {
        LessonEntity lesson = lessonRepository.findById(lessonSwapRequest.getLessonId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND));

        if(!lessonSwapRequest.getSrcSectionId().equals(lessonSwapRequest.getDesSectionId())){
            SectionEntity sectionEntity = sectionRepository.findById(lessonSwapRequest.getDesSectionId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.SECTION_NOT_FOUND));
            lesson.setSection(sectionEntity);
        }

        if(lesson.getPosition() > lessonSwapRequest.getToPosition()){
            lessonRepository.updatePositionMoveUp(lessonSwapRequest.getCourseId(),
                    lesson.getPosition(),
                    lessonSwapRequest.getToPosition(),
                    lesson.getId()
            );
        }
        else if(lesson.getPosition() < lessonSwapRequest.getToPosition()){
            lessonRepository.updatePositionMoveDown(lessonSwapRequest.getCourseId(),
                    lesson.getPosition(),
                    lessonSwapRequest.getToPosition(),
                    lesson.getId()
            );
        }
        lesson.setPosition(lessonSwapRequest.getToPosition());
        return mappingOne(lessonRepository.save(lesson));
    }

    @Override
    public Lesson updateInfo(String lessonId, LessonRequest lessonRequest) {
        LessonEntity lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND));
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setCode(HandleString.strToCode(lessonRequest.getTitle()));
        if(lessonRequest.getFile() != null){
            lesson.setVideo(uploadAudio(lessonRequest));
        }
        return mappingOne(lessonRepository.save(lesson));
    }

    @Override
    public Lesson delete(String lessonId) {
        return null;
    }

    private Lesson openNextLesson(LessonEntity lessonEntity,UserPrincipal userPrincipal){
        LessonEntity lesson = lessonRepository.findNextLesson( lessonEntity.getCourse().getId(), lessonEntity.getPosition() + 1);
        if(lesson == null) return null;
        System.out.println(lesson.getId());
        return unblockLesson(lesson,userPrincipal);
    }
    private Lesson unblockLesson(LessonEntity lessonEntity,UserPrincipal userPrincipal){
        lessonUserRepository.updateUnblock(userPrincipal.getId(),lessonEntity.getId());
        return mappingOne(lessonEntity);
    }
    @Override
    public Lesson mappingOne(LessonEntity lessonEntity) {
        return null;
    }



    private LessonEntity createLessonEntity(LessonRequest lessonRequest){
        StringBuilder video = new StringBuilder() ;
        if(lessonRequest.getFile() != null){
            video.append(uploadAudio(lessonRequest));
        }

        LessonEntity lesson = lessonMapper.toLessonEntity(lessonRequest);
        CourseEntity course = courseRepository.findById(lessonRequest.getCourseId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
        SectionEntity section = sectionRepository.findById(lessonRequest.getSectionId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.SECTION_NOT_FOUND));

        int total  =  lessonRepository.findLastBySectionIdAndCourseId(course.getId(),section.getId()).orElse(new LessonEntity()).getPosition();
        if(total == 0){
            total = lessonRepository.countByCourseId(course.getId());
        }
        updatePositionLesson(course.getId(),total);
        lesson.setCourse(course);
        lesson.setSection(section);
        lesson.setVideo(video.toString());
        lesson.setPosition(++total);
        lesson.setCode(HandleString.strToCode(lessonRequest.getTitle()));
        return lesson;
    }
    @Async
    private void updatePositionLesson(String courseId,int total){
        lessonRepository.updatePositionLesson(courseId,total);
    }
    private String uploadAudio(LessonRequest lessonRequest){
        FileRequest fileRequest = FileRequest.builder()
                .file(lessonRequest.getFile())
                .folderParentId(lessonRequest.getCourseId())
                .build();
        return fileService.uploadFile(fileRequest);

    }
    @Override
    public List<Lesson> mappingList(List<LessonEntity> lessonEntityList){
        return lessonEntityList.stream()
                .map(lessonMapper::toLesson)
                .toList();
    }
    @Override
    public List<Lesson> findAll() {
        return mappingList(lessonRepository.findAll());
    }

    @Override
    public Lesson findByCode(String lessonCode) {
        return lessonMapper.toLesson(lessonRepository.findByCode(lessonCode)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LESSON_NOT_FOUND)));
    }
}
