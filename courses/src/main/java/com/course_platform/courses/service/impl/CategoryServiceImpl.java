package com.course_platform.courses.service.impl;

import com.course_platform.courses.dto.request.CategoryRequest;
import com.course_platform.courses.dto.response.Category;
import com.course_platform.courses.entity.CategoryEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.CategoryMapper;
import com.course_platform.courses.repository.CategoryRepository;
import com.course_platform.courses.service.CategoryService;
import com.course_platform.courses.utils.HandleString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategory)
                .toList();
    }

    @Override
    public Category create(CategoryRequest categoryRequest) {
        CategoryEntity category = categoryMapper.toCategoryEntity(categoryRequest);
        category.setCode(HandleString.strToCode(categoryRequest.getName()));

        return mappingOne(categoryRepository.save(category));
    }

    @Override
    public Category update(String categoryId, CategoryRequest categoryRequest) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setName(categoryRequest.getName());
        category.setCode(HandleString.strToCode(categoryRequest.getName()));
        return mappingOne(categoryRepository.save(category));
    }

    @Override
    public Category mappingOne(CategoryEntity categoryEntity) {
        return categoryMapper.toCategory(categoryEntity);
    }

    @Override
    public List<Category> mappingList(List<CategoryEntity> categoryEntity) {
        return null;
    }
}
