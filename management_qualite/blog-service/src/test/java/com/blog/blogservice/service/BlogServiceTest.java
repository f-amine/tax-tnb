package com.blog.blogservice.service;

import com.blog.blogservice.client.SocialClient;
import com.blog.blogservice.dto.Comment;
import com.blog.blogservice.dto.Likes;
import com.blog.blogservice.model.Blog;
import com.blog.blogservice.repository.BlogRepository;
import com.blog.blogservice.response.FullBlogResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private SocialClient socialClient;

    @InjectMocks
    private BlogService blogService;

    @Test
    public void BlogService_save_ReturnsBlog() {
        //arrange
        Blog blog = Blog.builder()
                .title("Test")
                .content("TestContent")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();

        when(blogRepository.save(blog)).thenReturn(blog);

        //act
        Blog savedBlog = blogService.save(blog);

        //assert
        assertEquals(blog, savedBlog);
    }

    // ... similar tests for findAll(), findById(), delete()

    @Test
    public void BlogService_findAllWithComments_ReturnsFullBlogResponse() {
        //arrange
        Blog blog = Blog.builder()
                .title("Test")
                .content("TestContent")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        Likes like = Likes.builder().build();
        List<Likes> likes = Collections.singletonList(like);
        Comment comment = Comment.builder()
                .comment_text("TestComment")
                .user_id(1L)
                .likes(likes)
                .build();
        List<Comment> comments = Collections.singletonList(comment);

        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        when(socialClient.findAllCommentsByBlog(1L)).thenReturn(comments);

        FullBlogResponse expectedResponse = FullBlogResponse.builder()
                .title(blog.getTitle())
                .author(blog.getAuthor())
                .content(blog.getContent())
                .category(blog.getCategory())
                .commentList(comments)
                .publicationDate(blog.getPublicationDate())
                .author(blog.getAuthor())
                .city(blog.getCity())
                .build();

        //act
        FullBlogResponse actualResponse = blogService.findAllWithComments(1L);

        //assert
        assertEquals(expectedResponse.getTitle(), actualResponse.getTitle());
        assertEquals(expectedResponse.getAuthor(), actualResponse.getAuthor());
        assertEquals(expectedResponse.getContent(), actualResponse.getContent());
        assertEquals(expectedResponse.getCategory(), actualResponse.getCategory());
        assertEquals(expectedResponse.getCommentList(), actualResponse.getCommentList());
        assertEquals(expectedResponse.getPublicationDate(), actualResponse.getPublicationDate());
        assertEquals(expectedResponse.getCity(), actualResponse.getCity());
    }
}