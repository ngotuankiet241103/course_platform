package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.CategoryRequest;
import com.course_platform.courses.entity.CategoryEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    private CategoryRequest categoryRequest;
    private CategoryEntity categoryEntity;
    @BeforeEach
    void initData(){
        categoryRequest = CategoryRequest.builder()
                .name("technology")
                .build();
        categoryEntity = CategoryEntity.builder()
                .id("36cc2afd-b14e-4723-84fb-48b2034f445d")
                .code("technology")
                .code("technology")
                .build();
    }
    @Test
    void createCategory_validRequest_success(){
        // GIVEN
        when(categoryRepository.save(any())).thenReturn(categoryEntity);
        // WHEN
        var response = categoryService.create(categoryRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("36cc2afd-b14e-4723-84fb-48b2034f445d");
        assertThat(response.getCode()).isEqualTo("technology");
    }
    @Test
    void updateCategory_validRequest_success(){
        // GIVEN
        String categoryId = "36cc2afd-b14e-4723-84fb-48b2034f445d";
        categoryRequest.setName("cong nghe");
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(categoryEntity));
        when(categoryRepository.save(any())).thenReturn(categoryEntity);
        // WHEN
        var response = categoryService.update(categoryId,categoryRequest);
        // THEN
        assertThat(response.getId()).isEqualTo("36cc2afd-b14e-4723-84fb-48b2034f445d");
        assertThat(response.getCode()).isEqualTo("cong nghe");
    }
    @Test
    void updateCategory_invalidCategory_fail(){
        // GIVEN
        String categoryId = "36cc2afd-b14e-4723-84fb-48b2034f44d";
        categoryRequest.setName("cong nghe");

        // WHEN
        var exception = assertThrows(CustomRuntimeException.class, () -> categoryService.update(categoryId,categoryRequest));
        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Category not found");
    }
}
