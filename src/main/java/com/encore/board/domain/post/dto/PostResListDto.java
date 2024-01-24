package com.encore.board.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostResListDto {
    private Long id;
    private String title;
    private String author_email;
}
