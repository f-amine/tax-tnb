package com.blog.blogservice.response;

import com.blog.blogservice.dto.Comment;
import com.blog.blogservice.model.Category;
import com.blog.blogservice.model.City;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullBlogResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime publicationDate;
    private Long author;
    private City city;
    private Category category;
    List<Comment> commentList;
}
