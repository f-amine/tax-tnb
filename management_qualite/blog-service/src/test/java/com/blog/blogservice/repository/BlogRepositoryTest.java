package com.blog.blogservice.repository;

import com.blog.blogservice.model.Blog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void BlogRepository_save_ReturnsBlog() {
        //arrange
        Blog blog = Blog.builder()
                .title("TestTitle")
                .content("TestContent")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        //act
        Blog savedBlog = blogRepository.save(blog);
        //assert
        Assertions.assertNotNull(savedBlog);
        Assertions.assertEquals(blog.getTitle(), savedBlog.getTitle());
        Assertions.assertEquals(blog.getContent(), savedBlog.getContent());
    }

    @Test
    public void BlogRepository_findById_ReturnsBlog() {
        //arrange
        Blog blog = Blog.builder()
                .title("TestTitle")
                .content("TestContent")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        Blog savedBlog = blogRepository.save(blog);
        //act
        Blog foundBlog = blogRepository.findById(savedBlog.getId()).orElse(null);
        //assert
        Assertions.assertNotNull(foundBlog);
        Assertions.assertEquals(savedBlog.getId(), foundBlog.getId());
    }

    @Test
    public void BlogRepository_delete_DeletesBlog() {
        //arrange
        Blog blog = Blog.builder()
                .title("TestTitle")
                .content("TestContent")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        Blog savedBlog = blogRepository.save(blog);
        //act
        blogRepository.delete(savedBlog);
        //assert
        Assertions.assertEquals(0, blogRepository.findAll().size());
    }

    @Test
    public void BlogRepository_findAll_ReturnsAllBlogs() {
        //arrange
        Blog blog = Blog.builder()
                .title("TestTitle")
                .content("TestContent")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        Blog blog1 = Blog.builder()
                .title("TestTitle2")
                .content("TestContent2")
                .publicationDate(LocalDateTime.now())
                .author(2L)
                .build();
        Blog savedBlog = blogRepository.save(blog);
        Blog savedBlog1 = blogRepository.save(blog1);
        //act
        List<Blog> blogs = blogRepository.findAll();
        //assert
        Assertions.assertEquals(2, blogs.size());
    }
    @Test
    public void BlogRepository_findAllByAuthor_ReturnsBlogsByAuthor() {
        //arrange
        Blog blog1 = Blog.builder()
                .title("TestTitle1")
                .content("TestContent1")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        Blog blog2 = Blog.builder()
                .title("TestTitle2")
                .content("TestContent2")
                .publicationDate(LocalDateTime.now())
                .author(1L)
                .build();
        blogRepository.save(blog1);
        blogRepository.save(blog2);
        //act
        List<Blog> blogs = blogRepository.findAllByAuthor(1L);
        //assert
        Assertions.assertEquals(2, blogs.size());
    }

    @Test
    public void BlogRepository_findTop5ByOrderByPublicationDateDesc_ReturnsTop5Blogs() {
        //arrange
        for (int i = 0; i < 9; i++) {
            Blog blog = Blog.builder()
                    .title("TestTitle" + i)
                    .content("TestContent" + i)
                    .publicationDate(LocalDateTime.now().plusDays(i))
                    .author(1L)
                    .build();
            blogRepository.save(blog);
        }
        //act
        List<Blog> blogs = blogRepository.findTop5ByOrderByPublicationDateDesc();
        //assert
        Assertions.assertEquals(5, blogs.size());
        Assertions.assertEquals("TestTitle8", blogs.get(0).getTitle());
    }
}