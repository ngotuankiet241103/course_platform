package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.CategoryRequest;
import com.course_platform.courses.dto.response.Category;
import com.course_platform.courses.entity.CategoryEntity;

import java.util.List;

public interface CategoryService extends BaseService<CategoryEntity,Category> {
    List<Category> findAll();

    Category create(CategoryRequest categoryRequest);

    Category update(String categoryId, CategoryRequest categoryRequest);
}
