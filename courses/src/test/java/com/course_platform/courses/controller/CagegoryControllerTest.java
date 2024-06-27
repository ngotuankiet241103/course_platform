package com.course_platform.courses.controller;

import com.course_platform.courses.auth.AuthRest;
import com.course_platform.courses.auth.RestConfig;
import com.course_platform.courses.dto.request.CategoryRequest;
import com.course_platform.courses.dto.request.CommentRequest;
import com.course_platform.courses.dto.request.CommentUpdateRequest;
import com.course_platform.courses.dto.response.Category;
import com.course_platform.courses.dto.response.Comment;
import com.course_platform.courses.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestConfig.class)
public class CagegoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    private CategoryRequest categoryRequest;
    private Category category;
    private String accessToken;
    private HttpHeaders headers;
    @Autowired
    private AuthRest authRest;
    private ObjectMapper objectMapper;
    private CommentUpdateRequest commentUpdateRequest;
    @Value("${api.prefix}/categories")
    private String api;

    @BeforeEach
    void initData(){
        objectMapper = new ObjectMapper();
        accessToken = authRest.login();
        headers  = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);

        categoryRequest = CategoryRequest.builder()
                .name("technology")
                .build();
        category = Category.builder()
                .id("36cc2afd-b14e-4723-84fb-48b2034f445d")
                .code("technology")
                .code("technology")
                .build();
    }
    @Test
    void createCategory_validRequest_success() throws Exception{
        // GIVEN
        String content = objectMapper.writeValueAsString(categoryRequest);
        when(categoryService.create(any())).thenReturn(category);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                        .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("36cc2afd-b14e-4723-84fb-48b2034f445d"));;
    }
    @Test
    void createCategory_invalidName_fail() throws Exception{
        // GIVEN
        categoryRequest.setName("");
        String content = objectMapper.writeValueAsString(categoryRequest);
        when(categoryService.create(any())).thenReturn(category);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Name must be not empty"));;
    }
    @Test
    void updateCategory_invalidName_fail() throws Exception{
        // GIVEN
        String categoryId = "36cc2afd-b14e-4723-84fb-48b2034f445d";
        categoryRequest.setName("");
        String content = objectMapper.writeValueAsString(categoryRequest);
        when(categoryService.create(any())).thenReturn(category);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/{category-id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Name must be not empty"));;
    }
    @Test
    void updateCategory_validRequest_success() throws Exception{
        // GIVEN
        category.setName("cong nghe");
        String categoryId = "36cc2afd-b14e-4723-84fb-48b2034f445d";
        categoryRequest.setName("");
        String content = objectMapper.writeValueAsString(categoryRequest);
        when(categoryService.create(any())).thenReturn(category);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.put(api + "/{category-id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Name must be not empty"));;
    }
    @Test
    void getCategories_validRequest_success() throws Exception{
        // GIVEN
        when(categoryService.findAll()).thenReturn(new ArrayList<>(List.of(category)));
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.get(api)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));

    }
}
