package com.encore.board.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostReqCreateDto {
    private String title;
    private String contents;
    private String email;
    private String appointment;
    private String appointmentTime;
}
