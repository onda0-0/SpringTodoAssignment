package com.sparta.springboardprac1.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long todoId;
    private String username;
    private String content;
}
