package com.sparta.springboardprac1.entity;


import com.sparta.springboardprac1.dto.TodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="todo")
@NoArgsConstructor
public class  Todo extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username", nullable = false)
    private String username;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Todo(TodoRequestDto requestDto) {
        this.username=requestDto.getUsername();
        this.title=requestDto.getTitle();
        this.contents=requestDto.getContents();
        this.password=requestDto.getPassword();
    }

}