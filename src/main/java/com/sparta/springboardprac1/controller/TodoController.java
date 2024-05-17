package com.sparta.springboardprac1.controller;

import com.sparta.springboardprac1.dto.TodoRequestDto;
import com.sparta.springboardprac1.dto.TodoResponseDto;
import com.sparta.springboardprac1.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TodoController {
    private final JdbcTemplate jdbcTemplate;
    public TodoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //일정등록
    @PostMapping("/todos")
    public TodoResponseDto createTodo(@RequestBody TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto);

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        todo.setCreatedAt(now);
        todo.setUpdatedAt(now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO todo (username,title, contents, password, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, todo.getUsername());
                    preparedStatement.setString(2, todo.getTitle());
                    preparedStatement.setString(3, todo.getContents());
                    preparedStatement.setString(4, todo.getPassword());
                    preparedStatement.setTimestamp(5, todo.getCreatedAt());
                    preparedStatement.setTimestamp(6, todo.getUpdatedAt());
                    return preparedStatement;
                },
                keyHolder);

        Long id = keyHolder.getKey().longValue();
        todo.setId(id);

        TodoResponseDto todoResponseDto = new TodoResponseDto(todo);

        return todoResponseDto;

    }

    //전체일정조회
    @GetMapping("/todos")
    public List<TodoResponseDto> getTodos() {
        // Map To List
        List<TodoResponseDto> responseList = todoList.values().stream()
                .map(TodoResponseDto::new).toList();

        return responseList;
    }

    //선택일정조회
    @GetMapping("/todos/{id}")
    public List<TodoResponseDto> getTodos(Long id){
        String sql = "SELECT * FROM todo WHERE id = ?";

        return jdbcTemplate.query(sql, new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String manager = rs.getString("username");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                Timestamp updatedAt = rs.getTimestamp("updatedAt");
                return new TodoResponseDto(id, title, content, manager, createdAt,updatedAt);
            }
        }, id);
    }

    @GetMapping("/Todo/All")
    public List<TodoResponseDto> getAllTodo(){
        String sql = "SELECT * FROM Todo order by date DESC";

        return jdbcTemplate.query(sql, new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String manager = rs.getString("manager");
                String date = rs.getString("date");
                return new TodoResponseDto(id, title, content, manager, date);
            }
        });
    }

    @PutMapping("/Todo")
    public Long updateTodo(@RequestParam Long id, @RequestParam String password, @RequestBody TodoRequestDto requestDto) {
        Todo Todo = findIdPwd(id, password);
        if(Todo != null) {
            String sql = "UPDATE Todo SET title = ?, content = ?, manager = ? WHERE id = ? AND password = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getContent(), requestDto.getManager(), id, password);

            return id;
        } else {
            throw new IllegalArgumentException("일정이 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/Todo")
    public Long deleteTodo(@RequestParam Long id, @RequestParam String password){
        Todo Todo = findIdPwd(id, password);
        if(Todo != null){
            String sql = "DELETE FROM Todo WHERE id = ? AND password = ?";
            jdbcTemplate.update(sql, id, password);

            return id;
        } else{
            throw new IllegalArgumentException("일정이 존재하지 않습니다.");
        }
    }

    private Todo findIdPwd(Long id, String password) {
        String sql = "SELECT * FROM Todo WHERE id = ? AND password = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Todo Todo = new Todo();
                Todo.setTitle(resultSet.getString("title"));
                Todo.setContent(resultSet.getString("content"));
                Todo.setManager(resultSet.getString("manager"));
                return Todo;
            } else {
                return null;
            }
        }, id, password);
    }

}