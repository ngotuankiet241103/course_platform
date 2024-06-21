package com.course_platform.courses.service.impl;

import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.request.OrderRequest;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.OrderEntity;
import com.course_platform.courses.entity.OrderId;
import com.course_platform.courses.entity.UserEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.OrderRepository;
import com.course_platform.courses.repository.UserRepository;
import com.course_platform.courses.service.LessonUserService;
import com.course_platform.courses.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LessonUserService lessonUserService;
    @Transactional
    @Override
    public String create(OrderRequest orderRequest) {
        OrderEntity order = createOrderEntity(orderRequest);
        orderRepository.save(order);
        return "Buy course success";
    }
    @Async
    private void openCourse(CourseEntity course,String userId){
        lessonUserService.create(course,userId);
    }
    private OrderEntity createOrderEntity(OrderRequest orderRequest){
        CourseEntity course = courseRepository.findById(orderRequest.getCourseId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderId orderId = new OrderId(course.getId(),userId);

        openCourse(course,userId);
        return OrderEntity.builder()
                .timeOrder(new Date())
                .id(orderId)
                .build();

    }
}
