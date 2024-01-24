package com.encore.board.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostReqUpdateDto {
    private String title;
    private String contents;
}
