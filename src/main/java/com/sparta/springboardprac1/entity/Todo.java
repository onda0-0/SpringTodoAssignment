package com.sparta.springboardprac1.entity;


import com.sparta.springboardprac1.dto.TodoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Todo {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private String password;
    // 현재 날짜와 시간으로 객체 생성
    private Timestamp createdAt;
    private Timestamp updatedAt;



    public Todo(TodoRequestDto requestDto) {
        this.username=requestDto.getUsername();
        this.title=requestDto.getTitle();
        this.contents=requestDto.getContents();
        this.password=requestDto.getPassword();

    }
}