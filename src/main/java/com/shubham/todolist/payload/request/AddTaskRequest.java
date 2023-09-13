package com.shubham.todolist.payload.request;

import com.shubham.todolist.model.Task;
import lombok.Data;

import java.util.UUID;

@Data
public class AddTaskRequest {
    private Task task;
    private UUID userId;
}
