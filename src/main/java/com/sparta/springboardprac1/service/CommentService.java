package com.sparta.springboardprac1.service;

import com.sparta.springboardprac1.dto.CommentRequestDto;
import com.sparta.springboardprac1.dto.CommentResponseDto;
import com.sparta.springboardprac1.entity.Comment;
import com.sparta.springboardprac1.entity.Todo;
import com.sparta.springboardprac1.exception.BadRequestException;
import com.sparta.springboardprac1.exception.ForbiddenException;
import com.sparta.springboardprac1.exception.NotFoundException;
import com.sparta.springboardprac1.repository.CommentRepository;
import com.sparta.springboardprac1.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TodoRepository todoRepository) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
    }

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        if (requestDto.getTodoId() == null || requestDto.getContent() == null || requestDto.getContent().isEmpty()) {
            throw new BadRequestException("일정 ID와 댓글 내용은 필수입니다.");
        }

        Todo todo = todoRepository.findById(requestDto.getTodoId())
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));
        Comment comment = new Comment(requestDto, todo);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getCommentsByTodoId(Long todoId) {
        List<Comment> comments = commentRepository.findByTodoId(todoId);
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        if (requestDto.getContent() == null || requestDto.getContent().isEmpty()) {
            throw new BadRequestException("댓글 내용은 필수입니다.");
        }

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("선택한 댓글은 존재하지 않습니다."));

        if (!comment.getUsername().equals(requestDto.getUsername())) {
            throw new ForbiddenException("해당 댓글의 사용자가 아닙니다.");
        }

        comment.setContent(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id, String username) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("선택한 댓글은 존재하지 않습니다."));

        if (!comment.getUsername().equals(username)) {
            throw new ForbiddenException("해당 댓글의 사용자가 아닙니다.");
        }
        commentRepository.delete(comment);
    }
}
