package com.sparta.springboardprac1.dto;

import com.sparta.springboardprac1.entity.Todo;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.username = todo.getUsername();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.createdAt=getCreatedAt();
        this.updatedAt=getUpdatedAt();
    }
}