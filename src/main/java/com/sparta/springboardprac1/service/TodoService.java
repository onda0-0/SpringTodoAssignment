package com.sparta.springboardprac1.service;

import com.sparta.springboardprac1.dto.TodoRequestDto;
import com.sparta.springboardprac1.dto.TodoResponseDto;
import com.sparta.springboardprac1.entity.Todo;
import com.sparta.springboardprac1.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        todo.setCreatedAt(now);
        todo.setUpdatedAt(now);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public List<TodoResponseDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(TodoResponseDto::new).collect(Collectors.toList());
    }

    public TodoResponseDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id);
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id);
        if (todo == null) {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
        if (!todo.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        todo.setTitle(requestDto.getTitle());
        todo.setContents(requestDto.getContents());
        todo.setPassword(requestDto.getPassword());
        todo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        todoRepository.update(todo);
        return new TodoResponseDto(todo);
    }

    public void deleteTodo(Long id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id);
        if (todo == null) {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
        if (!todo.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        todoRepository.delete(id);
    }
}

