package com.blog.blogservice.service;

import com.blog.blogservice.model.Blog;
import com.blog.blogservice.model.Category;
import com.blog.blogservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long aLong) {
        return categoryRepository.findById(aLong);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
