package com.example.Project.management.dto;

import com.example.Project.management.entities.Status;
import lombok.Data;

@Data
public class TaskRequest {
    private String name;
    private String status;
    private String assignedDate;
    private String completionDate;
    private String   assignedTo;
    private String  assignedBy;
    private long projectId;

}
