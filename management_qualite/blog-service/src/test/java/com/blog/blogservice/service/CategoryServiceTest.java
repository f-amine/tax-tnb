package com.blog.blogservice.service;

import com.blog.blogservice.model.Category;
import com.blog.blogservice.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void CategoryService_save_ReturnsCategory() {
        //arrange
        Category category = Category.builder()
                .name("Test")
                .build();

        when(categoryRepository.save(category)).thenReturn(category);

        //act
        Category savedCategory = categoryService.save(category);

        //assert
        assertEquals(category, savedCategory);
    }

    @Test
    public void CategoryService_findAll_ReturnsAllCategories() {
        //arrange
        Category category1 = Category.builder().name("Test1").build();
        Category category2 = Category.builder().name("Test2").build();
        List<Category> expectedCategories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        //act
        List<Category> actualCategories = categoryService.findAll();

        //assert
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    public void CategoryService_findById_ReturnsCategory() {
        //arrange
        Category category = Category.builder().name("Test").build();
        Optional<Category> expectedCategory = Optional.of(category);

        when(categoryRepository.findById(anyLong())).thenReturn(expectedCategory);

        //act
        Optional<Category> actualCategory = categoryService.findById(1L);

        //assert
        assertEquals(expectedCategory, actualCategory);
    }

//    @Test
//    public void CategoryService_delete_DeletesCategory() {
//        //arrange
//        Category category = Category.builder().name("Test").build();
//
//        //act
//        categoryService.delete(category);
//
//        //assert
//        verify(categoryRepository, times(1)).delete(category);
//    }
}