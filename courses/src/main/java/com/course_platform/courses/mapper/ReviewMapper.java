package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.ReviewRequest;
import com.course_platform.courses.dto.response.Review;
import com.course_platform.courses.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewEntity toReviewEntity(ReviewRequest reviewRequest);
    @Mapping(target = "user",ignore = true)
    Review toReview(ReviewEntity reviewEntity);
}
