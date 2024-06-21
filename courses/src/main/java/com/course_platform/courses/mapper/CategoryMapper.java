package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.CategoryRequest;
import com.course_platform.courses.dto.response.Category;
import com.course_platform.courses.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryEntity categoryEntity);
    CategoryEntity toCategoryEntity(CategoryRequest categoryRequest);
}
