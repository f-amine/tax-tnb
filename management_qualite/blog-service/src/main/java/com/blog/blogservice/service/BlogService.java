package com.blog.blogservice.service;


import com.blog.blogservice.client.SocialClient;
import com.blog.blogservice.model.Blog;
import com.blog.blogservice.repository.BlogRepository;
import com.blog.blogservice.response.FullBlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final SocialClient socialClient;
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    public void delete(Blog entity) {
        blogRepository.delete(entity);
    }

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public FullBlogResponse findAllWithComments(Long blogId) {
        var blog = blogRepository.findById(blogId).orElse(Blog.builder().title("Not found").content("Not found").build());
        var comments = socialClient.findAllCommentsByBlog(blogId);//find all comments from social microservice


        return FullBlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .author(blog.getAuthor())
                .content(blog.getContent())
                .category(blog.getCategory())
                .commentList(comments)
                .publicationDate(blog.getPublicationDate())
                .author(blog.getAuthor())
                .city(blog.getCity())
                .build();
    }

    public List<FullBlogResponse> findAllBlogsByUser(Long author) {
        List<Blog> blogs = blogRepository.findAllByAuthor(author);
        return getFullBlogResponses(blogs);
    }


    @Transactional
    public List<FullBlogResponse> findLatestBlogs() {
            List<Blog> blogs = blogRepository.findTop5ByOrderByPublicationDateDesc();
            return getFullBlogResponses(blogs);
        }

    private List<FullBlogResponse> getFullBlogResponses(List<Blog> blogs) {
        List<FullBlogResponse> fullBlogResponses = new ArrayList<>();

        for (Blog blog : blogs) {
            var comments = socialClient.findAllCommentsByBlog(blog.getId());
            FullBlogResponse fullBlogResponse = FullBlogResponse.builder()
                    .id(blog.getId())
                    .title(blog.getTitle())
                    .author(blog.getAuthor())
                    .content(blog.getContent())
                    .category(blog.getCategory())
                    .commentList(comments)
                    .publicationDate(blog.getPublicationDate())
                    .author(blog.getAuthor())
                    .city(blog.getCity())
                    .build();
            fullBlogResponses.add(fullBlogResponse);
        }

        return fullBlogResponses;
    }
}

