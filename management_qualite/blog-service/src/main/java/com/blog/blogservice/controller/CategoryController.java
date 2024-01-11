package com.blog.blogservice.controller;

import com.blog.blogservice.model.Category;
import com.blog.blogservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }
    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }
    @GetMapping(path ="/{id}")
    public Optional<Category> findById(@PathVariable ("id") Long id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
