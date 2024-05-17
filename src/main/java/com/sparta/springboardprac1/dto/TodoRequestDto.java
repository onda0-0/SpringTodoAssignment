package com.sparta.springboardprac1.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private String password;
}

