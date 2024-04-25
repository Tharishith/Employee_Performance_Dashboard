package com.example.Project.management.dto;

import com.example.Project.management.entities.Project;
import com.example.Project.management.entities.Status;
import com.example.Project.management.entities.Task;
import lombok.Data;

import java.util.List;

@Data
public class CompleteProjectResponse {

    private Long project_id;
    private String name;
    private String description;
    private Status status;
    private String department_name;
    private String assignedDate;
    private String completionDate;
    private String assignedTo;
    private String assignedBy;
    private List<Task>tasks;
   // private List<Project> projects;
}
