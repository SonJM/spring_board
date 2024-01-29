package com.encore.board.domain.author.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
