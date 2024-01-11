package com.blog.blogservice.controller;

import com.blog.blogservice.dto.Comment;
import com.blog.blogservice.model.Blog;
import com.blog.blogservice.response.FullBlogResponse;
import com.blog.blogservice.service.BlogService;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlogController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BlogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BlogService blogService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void BlogController_CreateBlog_ReturnsBlog() throws Exception {
        // Arrange
        Blog blog = Blog.builder()
                .title("Test Blog")
                .content("Test Content")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        String blogJson = objectMapper.writeValueAsString(blog);

        when(blogService.save(any(Blog.class))).thenReturn(blog);

        // Act & Assert
        mockMvc.perform(post("/api/blogs/blog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogJson))
                .andExpect(status().isOk())
                .andExpect(content().json(blogJson));
    }

    @Test
    public void BlogController_FindById_ReturnsBlog() throws Exception {
        // Arrange
        Blog blog = Blog.builder()
                .title("Test Blog")
                .content("Test Content")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        when(blogService.findById(anyLong())).thenReturn(Optional.of(blog));

        // Act & Assert
        mockMvc.perform(get("/api/blogs/blog/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(blog)));
    }

    @Test
    public void BlogController_FindAll_ReturnsAllBlogs() throws Exception {
        // Arrange
        Blog blog1 = Blog.builder().title("Test Blog 1").content("Test Content 1").publicationDate(LocalDateTime.now()).author(1L).build();
        Blog blog2 = Blog.builder().title("Test Blog 2").content("Test Content 2").publicationDate(LocalDateTime.now()).author(2L).build();
        List<Blog> blogs = Arrays.asList(blog1, blog2);

        when(blogService.findAll()).thenReturn(blogs);

        // Act & Assert
        mockMvc.perform(get("/api/blogs/blog")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(blogs)));
    }

    @Test
    public void BlogController_FindAllWithComments_ReturnsFullBlogResponse() throws Exception {
        // Arrange
        Blog blog = Blog.builder()
                .title("Test Blog")
                .content("Test Content")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        Comment comment = Comment.builder()
                .comment_text("Test Comment")
                .user_id(1L)
                .build();
        List<Comment> comments = Collections.singletonList(comment);
        FullBlogResponse fullBlogResponse = FullBlogResponse.builder()
                .title(blog.getTitle())
                .author(blog.getAuthor())
                .content(blog.getContent())
                .category(blog.getCategory())
                .commentList(comments)
                .publicationDate(blog.getPublicationDate())
                .author(blog.getAuthor())
                .city(blog.getCity())
                .build();
        when(blogService.findAllWithComments(anyLong())).thenReturn(fullBlogResponse);

        // Act & Assert
        mockMvc.perform(get("/api/blogs/blog/with-comments/{blog-id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fullBlogResponse)));
    }

    @Test
    public void BlogController_FindAllBlogsByUser_ReturnsFullBlogResponses() throws Exception {
        // Arrange
        Blog blog1 = Blog.builder()
                .title("Test Blog 1")
                .content("Test Content 1")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        Blog blog2 = Blog.builder()
                .title("Test Blog 2")
                .content("Test Content 2")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        List<Blog> blogs = Arrays.asList(blog1, blog2);
        List<FullBlogResponse> fullBlogResponses = new ArrayList<>();
        for (Blog blog : blogs) {
            Comment comment = Comment.builder()
                    .comment_text("Test Comment")
                    .user_id(1L)
                    .build();
            List<Comment> comments = Collections.singletonList(comment);
            FullBlogResponse fullBlogResponse = FullBlogResponse.builder()
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
        when(blogService.findAllBlogsByUser(anyLong())).thenReturn(fullBlogResponses);

        // Act & Assert
        mockMvc.perform(get("/api/blogs/blog/by-user/{author}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fullBlogResponses)));
    }
    @Test
    public void BlogController_FindTop5ByOrderByPublicationDateDesc_ReturnsTop5Blogs() throws Exception {
        // Arrange
        Blog blog1 = Blog.builder().title("Test Blog 1").content("Test Content 1").publicationDate(LocalDateTime.now().plusDays(5)).author(1L).build();
        Blog blog2 = Blog.builder().title("Test Blog 2").content("Test Content 2").publicationDate(LocalDateTime.now().plusDays(4)).author(2L).build();
        Blog blog3 = Blog.builder().title("Test Blog 3").content("Test Content 3").publicationDate(LocalDateTime.now().plusDays(3)).author(3L).build();
        Blog blog4 = Blog.builder().title("Test Blog 4").content("Test Content 4").publicationDate(LocalDateTime.now().plusDays(2)).author(4L).build();
        Blog blog5 = Blog.builder().title("Test Blog 5").content("Test Content 5").publicationDate(LocalDateTime.now().plusDays(1)).author(5L).build();
        List<Blog> blogs = Arrays.asList(blog1, blog2, blog3, blog4, blog5);
        List<FullBlogResponse> fullBlogResponses = new ArrayList<>();
        for (Blog blog : blogs) {
            Comment comment = Comment.builder()
                    .comment_text("Test Comment")
                    .user_id(1L)
                    .build();
            List<Comment> comments = Collections.singletonList(comment);
            FullBlogResponse fullBlogResponse = FullBlogResponse.builder()
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
        when(blogService.findLatestBlogs()).thenReturn(fullBlogResponses);

        // Act & Assert
        mockMvc.perform(get("/api/blogs/blog/top5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fullBlogResponses)));
    }
}