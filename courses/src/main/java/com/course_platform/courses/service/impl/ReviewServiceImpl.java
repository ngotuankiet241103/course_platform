package com.course_platform.courses.service.impl;

import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.request.ReviewRequest;
import com.course_platform.courses.dto.response.Review;
import com.course_platform.courses.dto.response.UserInfo;
import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.ReviewEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.ReviewMapper;
import com.course_platform.courses.mapper.UserMapper;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.OrderRepository;
import com.course_platform.courses.repository.ReviewRepository;
import com.course_platform.courses.repository.UserRepository;
import com.course_platform.courses.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper reviewMapper;
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final Converter<JwtAuthenticationToken,UserPrincipal> converter;
    @Override
    public Review mappingOne(ReviewEntity reviewEntity) {
        Review review = reviewMapper.toReview(reviewEntity);
        UserInfo userInfo = UserInfo.builder()
                .fullName(reviewEntity.getName())
                .image(String.valueOf(userRepository.findById(reviewEntity.getUserId()).orElse(null)))
                .build();
        review.setUser(userInfo);
        return review;
    }

    @Override
    public List<Review> mappingList(List<ReviewEntity> e) {
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }

    @Override
    public Review create(ReviewRequest reviewRequest) {
        ReviewEntity review = createReviewEntity(reviewRequest);

        return mappingOne(reviewRepository.save(review));
    }

    @Override
    public List<Review> findByCourseId(String courseId) {
        return mappingList(reviewRepository.findByCourseId(courseId));
    }

    private ReviewEntity createReviewEntity(ReviewRequest reviewRequest){
        ReviewEntity review = reviewMapper.toReviewEntity(reviewRequest);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserPrincipal user = converter.convert((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        CourseEntity course = courseRepository.findById(reviewRequest.getCourseId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
        boolean check = orderRepository.existsByIdUserIdAndIdCourseId(userId, course.getId());
        if(!check){
            throw new CustomRuntimeException(ErrorCode.ORDER_NOT_EXIST);
        }
        review.setCourse(course);
        review.setUserId(userId);
        review.setName(user.getName());
        return review;
    }

}
