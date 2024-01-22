package com.encore.board.domain.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorListResDto {
    private Long id;
    private String name;
    private String email;
}
