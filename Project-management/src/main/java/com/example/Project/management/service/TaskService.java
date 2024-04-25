package com.example.Project.management.service;

import com.example.Project.management.dto.TaskRequest;
import com.example.Project.management.dto.TaskResponse;
import com.example.Project.management.dto.UpdateStatusRequest;
import com.example.Project.management.dto.UserTasksResponse;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest);


    TaskResponse getTaskById(Long id);

    String deleteTask(Long id, Long task_id);

    String updateStatus(Long id, UpdateStatusRequest updateStatusRequest,String token);

    String updateTaskDetails(Long id, TaskRequest taskRequest);

    UserTasksResponse getTaskOfUser(String token);
}
