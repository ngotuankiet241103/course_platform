package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.ReviewRequest;
import com.course_platform.courses.dto.response.Review;
import com.course_platform.courses.entity.ReviewEntity;

import java.util.List;

public interface ReviewService extends BaseService<ReviewEntity, Review> {
    Review create(ReviewRequest reviewRequest);

    List<Review> findByCourseId(String courseId);
}
