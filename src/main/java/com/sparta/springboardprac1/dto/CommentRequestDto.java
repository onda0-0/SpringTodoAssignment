package com.sparta.springboardprac1.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
public class CommentRequestDto {
    @NotNull
    private Long todoId;
    @NotBlank
    private String username;
    @NotBlank
    private String content;
}
