package com.example.Project.management.entities;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long   task_id;
    @Column(unique = true)
    private String name;
    private Status status;
    private Long projectId;
    private String AssignedDate;
    private String CompletionDate;
    @Column(name ="assignedTo")
    private String assignedTo;

    private String AssignedBy;

}
