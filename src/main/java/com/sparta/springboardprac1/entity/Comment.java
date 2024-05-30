package com.sparta.springboardprac1.entity;

import com.sparta.springboardprac1.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    private String username;
    private String content;

    public Comment(CommentRequestDto requestDto, Todo todo) {
        this.todo = todo;
        this.username = requestDto.getUsername();
        this.content = requestDto.getContent();
    }
}
