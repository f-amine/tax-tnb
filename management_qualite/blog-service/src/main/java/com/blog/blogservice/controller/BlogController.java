package com.blog.blogservice.controller;

import com.blog.blogservice.model.Blog;
import com.blog.blogservice.response.FullBlogResponse;
import com.blog.blogservice.service.BlogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public Blog save(@RequestBody  Blog blog) {
        return blogService.save(blog);
    }

    @GetMapping(path ="/{id}")
    public Optional<Blog> findById(@PathVariable("id") Long id) {
        return blogService.findById(id);
    }

    @DeleteMapping
    public void delete(@RequestBody Blog entity) {
        blogService.delete(entity);
    }

    @GetMapping
    public List<Blog> findAll() {
        return blogService.findAll();
    }

    @GetMapping("/with-comments/{blog-id}")
    public ResponseEntity<FullBlogResponse> findAll(@PathVariable("blog-id") Long blogId) {
        return ResponseEntity.ok(blogService.findAllWithComments(blogId));
    }

    @Transactional
    @GetMapping("/by-user/{author}")
    public ResponseEntity<List<FullBlogResponse>> findAllBlogsByUser(@PathVariable("author") Long author) {
        return ResponseEntity.ok(blogService.findAllBlogsByUser(author));
    }

    @GetMapping("/top5")
    public ResponseEntity<List<FullBlogResponse>> findTop5ByOrderByPublicationDateDesc() {
        return ResponseEntity.ok(blogService.findLatestBlogs());
    }
}
