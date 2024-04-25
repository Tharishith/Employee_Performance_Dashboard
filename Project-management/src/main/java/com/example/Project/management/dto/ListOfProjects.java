package com.example.Project.management.dto;

import com.example.Project.management.entities.Project;
import lombok.Data;

import java.util.List;

@Data
public class ListOfProjects {

    private List<Project> projects;
}
