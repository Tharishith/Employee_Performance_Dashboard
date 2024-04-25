package com.example.Project.management.service;

import com.example.Project.management.dto.*;
import com.example.Project.management.entities.Project;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest projectRequest);


    List<CompleteProjectResponse> completeDetails(String token);

    String updateProjectDetails(Long id,ProjectRequest projectRequest);

    String updateStatus(Long id, UpdateStatusRequest updateStatusRequest);

    String deleteProject(Long id);

    List<CompleteProjectResponse> getAllProjects();

    ListofTasks getTasks(Long id);
}
