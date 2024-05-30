package com.sparta.springboardprac1.repository;

import com.sparta.springboardprac1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTodoId(Long todoId);
}