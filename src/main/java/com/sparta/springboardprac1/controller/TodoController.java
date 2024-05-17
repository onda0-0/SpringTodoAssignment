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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final JdbcTemplate jdbcTemplate;
    public TodoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Todo> todoRowMapper() {
        return (rs, rowNum) -> {
            Todo todo = new Todo();
            todo.setId(rs.getLong("id"));
            todo.setUsername(rs.getString("username"));
            todo.setTitle(rs.getString("title"));
            todo.setContents(rs.getString("contents"));
            todo.setPassword(rs.getString("password"));
            todo.setCreatedAt(rs.getTimestamp("createdAt"));
            todo.setUpdatedAt(rs.getTimestamp("updatedAt"));
            return todo;
        };
    }

    //일정등록
    @PostMapping
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
    @GetMapping
    public List<TodoResponseDto> getAllTodos(){
        String sql = "SELECT * FROM todo order by createdAt DESC";

        List<Todo> todos = jdbcTemplate.query(sql, todoRowMapper());
        return todos.stream().map(TodoResponseDto::new).collect(Collectors.toList());
    }

    //선택일정조회
    @GetMapping("/{id}")
    public TodoResponseDto  getTodoById(@PathVariable Long id){
        String sql = "SELECT * FROM todo WHERE id = ?";

        Todo todo = jdbcTemplate.queryForObject(sql, new Object[]{id}, todoRowMapper());
        return new TodoResponseDto(todo);
    }

    //선택일정수정
    @PutMapping("/{id}")
    public TodoResponseDto updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        String sqlSelect = "SELECT * FROM todo WHERE id = ?";
        Todo todo = jdbcTemplate.queryForObject(sqlSelect, new Object[]{id}, todoRowMapper());

        if (todo == null) {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }

        todo.setTitle(requestDto.getTitle());
        todo.setContents(requestDto.getContents());
        todo.setPassword(requestDto.getPassword());
        todo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        String sqlUpdate = "UPDATE todo SET title = ?, contents = ?, password = ?, updatedAt = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdate, todo.getTitle(), todo.getContents(), todo.getPassword(), todo.getUpdatedAt(), id);
        return new TodoResponseDto(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

   /* private Todo findIdPwd(Long id, String password) {
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
    }*/

}