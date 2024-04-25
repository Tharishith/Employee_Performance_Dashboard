package com.example.Project.management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;
    @Column(unique = true)
    private String name;
    private String description;
    private Status status;
    private String department_name;
    private String assignedDate;
    private String completionDate;
    @Column(name ="assignedTo")
    private String assignedTo;
    private String assignedBy;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;

}
