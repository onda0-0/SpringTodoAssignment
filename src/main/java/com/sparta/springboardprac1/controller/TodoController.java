package com.sparta.springboardprac1.controller;

import com.sparta.springboardprac1.dto.TodoRequestDto;
import com.sparta.springboardprac1.dto.TodoResponseDto;
import com.sparta.springboardprac1.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoResponseDto createTodo(@Valid @RequestBody TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
        //TodoResponseDto todoResponseDto = todoService.createTodo(requestDto);
        //return ResponseEntity.status(201).body(todoResponseDto); // 201 Created
    }

    @GetMapping
    public List<TodoResponseDto> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoResponseDto getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PutMapping("/{id}")
    public TodoResponseDto updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto);
    }
}
