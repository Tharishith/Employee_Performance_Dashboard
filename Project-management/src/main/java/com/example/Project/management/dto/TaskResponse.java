package com.example.Project.management.dto;

import com.example.Project.management.entities.Status;
import lombok.Data;

@Data
public class TaskResponse {
    private Long task_id;
    private String name;
    private Status status;
    private String AssignedDate;
    private String CompletionDate;
    private String  AssignedTo;
    private String  AssignedBy;
    private Long projectId;
}
