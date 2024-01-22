package com.encore.board.domain.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthorSaveReqDto {
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
