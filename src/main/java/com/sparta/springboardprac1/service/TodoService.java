package com.sparta.springboardprac1.service;

import com.sparta.springboardprac1.dto.TodoRequestDto;
import com.sparta.springboardprac1.dto.TodoResponseDto;
import com.sparta.springboardprac1.entity.Todo;
import com.sparta.springboardprac1.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        // RequestDto -> Entity
        Todo todo = new Todo(requestDto);
        // DB 저장
        Todo saveTodo = todoRepository.save(todo);
        // Entity -> ResponseDto
        TodoResponseDto todoResponseDto = new TodoResponseDto(saveTodo);

        return todoResponseDto;
    }

    public List<TodoResponseDto> getAllTodos() {
        //DB조회
        List<Todo> todos = todoRepository.findAllByOrderByUpdatedAtDesc();
        return todos.stream().map(TodoResponseDto::new).collect(Collectors.toList());
    }

    public TodoResponseDto getTodoById(Long id) {
        Todo todo= todoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto  updateTodo(Long id, TodoRequestDto requestDto) {
        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));
        if (!todo.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        todo.setTitle(requestDto.getTitle());
        todo.setContents(requestDto.getContents());
        todo.setPassword(requestDto.getPassword());
        return new TodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(Long id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));
        if (!todo.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        todoRepository.delete(todo);
    }
}

