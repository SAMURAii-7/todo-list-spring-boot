package com.shubham.todolist.dao;

import com.shubham.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDao extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
