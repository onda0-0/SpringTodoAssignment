package com.sparta.springboardprac1.controller;

import com.sparta.springboardprac1.dto.CommentRequestDto;
import com.sparta.springboardprac1.dto.CommentResponseDto;
import com.sparta.springboardprac1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todos/{todoId}/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    @GetMapping
    public List<CommentResponseDto> getCommentsByTodoId(@PathVariable Long todoId) {
        return commentService.getCommentsByTodoId(todoId);
    }

    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @RequestParam String username) {
        commentService.deleteComment(id,username);
    }
}
