package com.blog.blogservice.client;

import com.blog.blogservice.dto.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "social-service", url = "${application.config.social-url}/social")
public interface SocialClient {
    @GetMapping("/comment/blog/{blog-id}")
    List<Comment> findAllCommentsByBlog(@PathVariable("blog-id") Long blogId) ;
}
