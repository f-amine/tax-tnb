package com.blog.blogservice.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private Long id ;
    private Long user_id;
    private String comment_text;
    private List<Likes> likes;
}
