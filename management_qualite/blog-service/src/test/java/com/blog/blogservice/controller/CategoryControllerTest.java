package com.blog.blogservice.controller;

import com.blog.blogservice.model.Category;
import com.blog.blogservice.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void CategoryController_CreateCategory_ReturnsCategory() throws Exception {
        // Arrange
        Category category = Category.builder()
                .name("Test Category")
                .build();
        String categoryJson = objectMapper.writeValueAsString(category);

        when(categoryService.save(any(Category.class))).thenReturn(category);

        // Act & Assert
        mockMvc.perform(post("/api/blogs/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk())
                .andExpect(content().json(categoryJson));

    }
    @Test
    public void CategoryController_FindById_ReturnsCategory() throws Exception {
        // Arrange
        Category category = Category.builder()
                .name("Test Category")
                .build();
        when(categoryService.findById(anyLong())).thenReturn(Optional.of(category));

        // Act & Assert
        mockMvc.perform(get("/api/blogs/category/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    public void CategoryController_FindAll_ReturnsAllCategories() throws Exception {
        // Arrange
        Category category1 = Category.builder().name("Test Category 1").build();
        Category category2 = Category.builder().name("Test Category 2").build();
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryService.findAll()).thenReturn(categories);

        // Act & Assert
        mockMvc.perform(get("/api/blogs/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categories)));
    }

}
