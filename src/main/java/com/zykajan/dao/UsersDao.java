package com.zykajan.dao;

import com.zykajan.data.User;
import com.zykajan.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String userByNameQuery = "select * from user where name = ?";

    @Autowired
    public UsersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByName(String name) {
        try {
            return this.jdbcTemplate.queryForObject(userByNameQuery, new Object[]{name}, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("User name=" + name + " doesn't exist");
        }
    }
}
