package com.sparta.springboardprac1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TodoRequestDto {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    @NotBlank
    private String password;
}

