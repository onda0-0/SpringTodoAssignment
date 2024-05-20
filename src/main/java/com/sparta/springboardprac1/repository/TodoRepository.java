package com.sparta.springboardprac1.repository;

import com.sparta.springboardprac1.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoRepository(JdbcTemplate jdbcTemplate) {
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

    public void save(Todo todo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO todo (username, title, contents, password, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, todo.getUsername());
            preparedStatement.setString(2, todo.getTitle());
            preparedStatement.setString(3, todo.getContents());
            preparedStatement.setString(4, todo.getPassword());
            preparedStatement.setTimestamp(5, todo.getCreatedAt());
            preparedStatement.setTimestamp(6, todo.getUpdatedAt());
            return preparedStatement;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        todo.setId(id);
    }

    public List<Todo> findAll() {
        String sql = "SELECT * FROM todo ORDER BY createdAt DESC";
        return jdbcTemplate.query(sql, todoRowMapper());
    }

    public Todo findById(Long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, todoRowMapper());
    }

    public void update(Todo todo) {
        String sql = "UPDATE todo SET title = ?, contents = ?, password = ?, updatedAt = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getContents(), todo.getPassword(), todo.getUpdatedAt(), todo.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
