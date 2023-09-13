package com.shubham.todolist.service;

import com.shubham.todolist.dao.TaskDao;
import com.shubham.todolist.dao.UserDao;
import com.shubham.todolist.model.Task;
import com.shubham.todolist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserDao userDao;

    public ResponseEntity<String> addTask(Task task, UUID userId) {
        Logger logger = LoggerFactory.getLogger(getClass());
        UUID uuid = UUID.randomUUID();
        task.setId(uuid);
        Optional<User> user = userDao.findById(userId);
        user.ifPresent(task::setUser); // user.ifPresent(u -> task.setUser(u));
        taskDao.save(task);
        try{
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred while adding task: ", e);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Task>> getAllTasks(UUID userId) {
        Logger logger = LoggerFactory.getLogger(getClass());

        try {
            List<Task> tasks = taskDao.findAllByUserId(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving tasks: ", e);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> editTask(UUID id, Task task) {
        Logger logger = LoggerFactory.getLogger(getClass());

        Optional<Task> oldTask = taskDao.findById(id);
        oldTask.ifPresent(value -> {
            value.setTitle(task.getTitle());
            taskDao.save(value);
        });
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            logger.error("An error occurred while updating the task: ", e);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteTask(UUID id) {
        Logger logger = LoggerFactory.getLogger(getClass());

        taskDao.deleteById(id);
        try{
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the task: ", e);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
