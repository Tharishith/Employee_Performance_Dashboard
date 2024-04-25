package com.example.Project.management.controller;

import com.example.Project.management.dto.TaskRequest;
import com.example.Project.management.dto.TaskResponse;
import com.example.Project.management.dto.UpdateStatusRequest;
import com.example.Project.management.dto.UserTasksResponse;
import com.example.Project.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @DeleteMapping("/delete/{project_id}/task/{task_id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long project_id,@PathVariable Long task_id){
        return ResponseEntity.ok(taskService.deleteTask(project_id,task_id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest updateStatusRequest,@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(taskService.updateStatus(id,updateStatusRequest,token));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTaskDetails(@PathVariable Long id,@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.updateTaskDetails(id,taskRequest));
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/user")
    public ResponseEntity<UserTasksResponse> getTasksOfUsers(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(taskService.getTaskOfUser(token));
    }
}
