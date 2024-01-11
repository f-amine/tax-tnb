package com.blog.blogservice.repository;

import com.blog.blogservice.model.Category;
import org.antlr.stringtemplate.language.Cat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void CategoryRepository_save_ReturnsCategory() {
        //arrange
        Category category = Category.builder()
                .name("Test")
                .build();
        //act
        Category savedCategory = categoryRepository.save(category);
        //assert
        Assertions.assertNotNull(savedCategory);
        Assertions.assertEquals(category.getName(), savedCategory.getName());
    }

    @Test
    public void CategoryRepository_findById_ReturnsCategory() {
        //arrange
        Category category = Category.builder()
                .name("Test")
                .build();
        Category savedCategory = categoryRepository.save(category);
        //act
        Category foundCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);
        //assert
        Assertions.assertNotNull(foundCategory);
        Assertions.assertEquals(savedCategory.getId(), foundCategory.getId());
    }

    @Test
    public void CategoryRepository_delete_DeletesCategory() {
        //arrange
        Category category = Category.builder()
                .name("Test")
                .build();
        Category savedCategory = categoryRepository.save(category);
        //act
        categoryRepository.delete(savedCategory);
        //assert
        Assertions.assertEquals(0, categoryRepository.findAll().size());
    }
    @Test
    public void CategoryRepository_findAll_ReturnsAllCategories() {
        //arrange
        Category category = Category.builder()
                .name("Test")
                .build();
        Category category1 = Category.builder()
                .name("Test2")
                .build();
        Category savedCategory = categoryRepository.save(category);
        Category savedCategory1 = categoryRepository.save(category1);
        //act
        List<Category> categories = categoryRepository.findAll();
        //assert
        Assertions.assertEquals(2, categories.size());
    }
}
