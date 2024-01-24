package com.encore.board.domain.author.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorUpdateReqDto {
    Long id;
    String name;
    String email;
    String password;
}
