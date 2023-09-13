package com.shubham.todolist.dao;

import com.shubham.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskDao extends JpaRepository<Task, UUID> {

    @Query(value = "SELECT * FROM task WHERE user_id = ?1", nativeQuery = true)
    List<Task> findAllByUserId(UUID userId);
}
