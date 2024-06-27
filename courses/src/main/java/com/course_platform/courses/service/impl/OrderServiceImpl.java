package com.course_platform.courses.service.impl;

import com.course_platform.courses.dto.request.OrderRequest;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.OrderEntity;
import com.course_platform.courses.entity.OrderId;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.OrderRepository;
import com.course_platform.courses.repository.UserRepository;
import com.course_platform.courses.service.LessonUserService;
import com.course_platform.courses.service.OrderService;
import com.course_platform.courses.utils.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LessonUserService lessonUserService;
    private CountDownLatch latch;
    @Transactional
    @Override
    public String create(OrderRequest orderRequest, String code) {
        OrderEntity order = createOrderEntity(orderRequest);
        order.setCode(code);

        orderRepository.save(order);
        return "Buy course success";
    }

    @Override
    public void updateStatus(String code, String status) {
        OrderEntity order = orderRepository.findByCode(code)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ORDER_FAILED));
        boolean check = status.equals("00");
        order.setCompleted(true);
        order.setOrderStatus(check ? OrderStatus.COMPLETED : OrderStatus.CANCEL);
        orderRepository.save(order);
        if(check){
            CourseEntity course = courseRepository.findById(order.getId().getCourseId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            openCourse(course, authentication.getName());
        }
        latch.countDown();

    }

    @Override
    public String generateUrl(String code, String redirectUrl) {


            OrderEntity order = orderRepository.findByCode(code)
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ORDER_FAILED));
            CourseEntity course = courseRepository.findById(order.getId().getCourseId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
            StringBuilder url = new StringBuilder(redirectUrl);
            url.append("?course_name=");
            url.append(course.getCode());
            url.append("&status=");
            url.append(order.getOrderStatus().getStatus());

            return url.toString();

    }

    @Override
    public String delayGenerateUrl(String code, String redirectUrl) {
        latch = new CountDownLatch(1);
        try {
            latch.await(15,TimeUnit.MINUTES);
            return generateUrl(code,redirectUrl);
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Async
    private void openCourse(CourseEntity course,String userId){
        lessonUserService.create(course,userId);
    }
    private OrderEntity createOrderEntity(OrderRequest orderRequest){
        CourseEntity course = courseRepository.findById(orderRequest.getCourseId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean check = course.getPrice() == 0;
        OrderId orderId = new OrderId(course.getId(),userId);
        if(check){
            openCourse(course,userId);
        }

        return OrderEntity.builder()
                .timeOrder(new Date())
                .id(orderId)
                .isCompleted(check)
                .orderStatus(check ? OrderStatus.COMPLETED : OrderStatus.PENDING)
                .build();

    }
}
