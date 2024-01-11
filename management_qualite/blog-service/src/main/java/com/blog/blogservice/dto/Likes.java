package com.blog.blogservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Likes {
        private Long commentId;
        private Long userId;
}
