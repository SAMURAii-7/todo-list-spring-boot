package com.shubham.todolist.controller;

import com.shubham.todolist.model.Task;
import com.shubham.todolist.payload.request.AddTaskRequest;
import com.shubham.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping({"", "/"})
    public String home() {
        return "Welcome to the Server!";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addTask(@RequestBody AddTaskRequest addTaskRequest) {
        return taskService.addTask(addTaskRequest.getTask(), addTaskRequest.getUserId());
    }

    @GetMapping("/getTasks")
    @ResponseBody
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam UUID userId) {
        return taskService.getAllTasks(userId);
    }

    @PutMapping("/edit/{id}")
    @ResponseBody
    public ResponseEntity<String> editTask(@PathVariable UUID id, @RequestBody Task task) {
        return taskService.editTask(id, task);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        return taskService.deleteTask(id);
    }
}
