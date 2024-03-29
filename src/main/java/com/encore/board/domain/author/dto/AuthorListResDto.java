package com.encore.board.domain.author.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorListResDto {
    private Long id;
    private String name;
    private String email;
}
