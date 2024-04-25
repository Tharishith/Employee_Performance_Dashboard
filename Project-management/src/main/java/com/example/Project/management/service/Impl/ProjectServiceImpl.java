package com.example.Project.management.service.Impl;

import com.example.Project.management.dto.*;
import com.example.Project.management.entities.Project;
import com.example.Project.management.entities.Status;
import com.example.Project.management.entities.Task;
import com.example.Project.management.repository.ProjectRepository;
import com.example.Project.management.repository.TaskRepository;
import com.example.Project.management.service.JwtService;
import com.example.Project.management.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JwtService jwtService;

    public ProjectResponse createProject(ProjectRequest projectRequest){
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setDepartment_name(projectRequest.getDepartment_name());
        project.setAssignedTo(projectRequest.getAssignedTo());
        project.setAssignedDate(projectRequest.getAssignedDate());
        project.setCompletionDate(projectRequest.getCompletionDate());
        project.setStatus(Status.valueOf(projectRequest.getStatus()));
        project.setAssignedBy(projectRequest.getAssignedBy());
        Project project1 = projectRepository.save(project);
        return MapToProjectResponse(project1);
    }


    public  List<CompleteProjectResponse> getAllProjects(){
        List<Project> allProjects = projectRepository.findAll();
        List<CompleteProjectResponse> responseProjectList = new ArrayList<>();
        for(Project project : allProjects){
            responseProjectList.add(MapToCompleteProjectResponse(project));
        }
        return responseProjectList;
    }
    public List<CompleteProjectResponse> completeDetails(String token)
    {
        var jwt = token.substring(7);
        String userEmail = jwtService.extractUserName(jwt);
        List<Project> managerProjects = projectRepository.findByAssignedTo(userEmail);
        List<Project> projecttask = new ArrayList<>();
        List<CompleteProjectResponse> response = new ArrayList<>();
        for(Project project : managerProjects){
            projecttask.add(project);
            response.add(MapToCompleteProjectResponse(project));
        }
        return response;
    }

    public String updateProjectDetails(Long id,ProjectRequest projectRequest){
        Project project = projectRepository.findById(id).get();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setDepartment_name(projectRequest.getDepartment_name());
        project.setAssignedTo(projectRequest.getAssignedTo());
        project.setAssignedDate(projectRequest.getAssignedDate());
        project.setCompletionDate(projectRequest.getCompletionDate());
        project.setStatus(Status.valueOf(projectRequest.getStatus()));
        project.setAssignedBy(projectRequest.getAssignedBy());
        projectRepository.save(project);
        return "Project is Updated Successfully";
    }

    private CompleteProjectResponse MapToCompleteProjectResponse(Project project) {
        CompleteProjectResponse completeProjectResponse = new CompleteProjectResponse();
        completeProjectResponse.setProject_id(project.getProject_id());
        completeProjectResponse.setName(project.getName());
        completeProjectResponse.setDescription(project.getDescription());
        completeProjectResponse.setDepartment_name(project.getDepartment_name());
        completeProjectResponse.setStatus(project.getStatus());
        completeProjectResponse.setAssignedDate(project.getAssignedDate());
        completeProjectResponse.setCompletionDate(project.getCompletionDate());
        completeProjectResponse.setAssignedTo(project.getAssignedTo());
        completeProjectResponse.setAssignedBy(project.getAssignedBy());
        completeProjectResponse.setTasks(project.getTasks());
        return completeProjectResponse;
    }

    private ProjectResponse MapToProjectResponse(Project project1) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setProject_id(project1.getProject_id());
        projectResponse.setName(project1.getName());
        projectResponse.setDescription(project1.getDescription());
        projectResponse.setDepartment_name(project1.getDepartment_name());
        projectResponse.setStatus(project1.getStatus());
        projectResponse.setAssignedDate(project1.getAssignedDate());
        projectResponse.setCompletionDate(project1.getCompletionDate());
        projectResponse.setAssignedTo(project1.getAssignedTo());
        projectResponse.setAssignedBy(project1.getAssignedBy());
        return projectResponse;
    }
    public String updateStatus(Long id, UpdateStatusRequest updateStatusRequest){
        Project project = projectRepository.findById(id).get();
        project.setStatus(Status.valueOf(updateStatusRequest.getStatus()));
        projectRepository.save(project);
        return "Status is updated successfully";
    }

    public String deleteProject(Long id){
        projectRepository.deleteById(id);
        return "Project is deleted successfully";
    }

    public ListofTasks getTasks(Long id){
        Project project = projectRepository.findById(id).get();
        ListofTasks listofTasks = new ListofTasks();
        List<Task> allTasks = project.getTasks();
        listofTasks.setTaskId(allTasks);
         return  listofTasks;
    }
}
