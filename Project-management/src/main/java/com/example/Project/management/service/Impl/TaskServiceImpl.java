package com.example.Project.management.service.Impl;

import com.example.Project.management.dto.TaskRequest;
import com.example.Project.management.dto.TaskResponse;
import com.example.Project.management.dto.UpdateStatusRequest;
import com.example.Project.management.dto.UserTasksResponse;
import com.example.Project.management.entities.Project;
import com.example.Project.management.entities.Status;
import com.example.Project.management.entities.Task;
import com.example.Project.management.repository.ProjectRepository;
import com.example.Project.management.repository.TaskRepository;
import com.example.Project.management.service.JwtService;
import com.example.Project.management.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ProjectRepository projectRepository;

    public TaskResponse createTask(TaskRequest taskRequest){
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setStatus(Status.valueOf(taskRequest.getStatus()));
        task.setAssignedTo(taskRequest.getAssignedTo());
        task.setAssignedBy(taskRequest.getAssignedBy());
        task.setAssignedDate(taskRequest.getAssignedDate());
        task.setCompletionDate(taskRequest.getCompletionDate());


        Optional<Project> opt = projectRepository.findById(taskRequest.getProjectId());
        if(opt.isPresent()){
            Project project = opt.get();
            List<Task> existingTasks = project.getTasks();
            task.setProjectId(taskRequest.getProjectId());
            existingTasks.add(task);
            task = taskRepository.save(task);
            projectRepository.save(project);
        }

        return MapToTaskResponse(task);
    }

    public  String updateTaskDetails(Long id, TaskRequest taskRequest){
        Task task = taskRepository.findById(id).get();
        task.setName(taskRequest.getName());
        task.setStatus(Status.valueOf(taskRequest.getStatus()));
        task.setAssignedTo(taskRequest.getAssignedTo());
        task.setAssignedBy(taskRequest.getAssignedBy());
        task.setAssignedDate(taskRequest.getAssignedDate());
        task.setCompletionDate(taskRequest.getCompletionDate());
        task.setProjectId(taskRequest.getProjectId());
        taskRepository.save(task);
        return "Task is updated successfully";
    }

    public TaskResponse MapToTaskResponse(Task task){
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTask_id(task.getTask_id());
        taskResponse.setProjectId(task.getProjectId());
        taskResponse.setName(task.getName());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setAssignedTo(task.getAssignedTo());
        taskResponse.setAssignedBy(task.getAssignedBy());
        taskResponse.setAssignedDate(task.getAssignedDate());
        taskResponse.setCompletionDate(task.getCompletionDate());
        return taskResponse;
    }
    public TaskResponse getTaskById(Long id){
        Task task = taskRepository.findById(id).get();
        return MapToTaskResponse(task);
    }
    public  String deleteTask(Long id, Long task_id){
        Project project = projectRepository.findById(id).get();
        List<Task> oldTasks = project.getTasks();
        List<Task> newTasks = new ArrayList<>();
        for(Task task : oldTasks){
            if(task.getTask_id() !=task_id){
                newTasks.add(task);
            }
        }
        project.setTasks(newTasks);
        projectRepository.save(project);
        taskRepository.deleteById(task_id);
        return "Task is deleted succesfully";
    }
    public String updateStatus(Long id, UpdateStatusRequest updateStatusRequest,String token){
        Task task = taskRepository.findById(id).get();
        var jwt = token.substring(7);
        String  userEmail = jwtService.extractUserName(jwt);
        String taskAssignedByEmail = task.getAssignedBy();
        String taskAssignedToEmail = task.getAssignedTo();
        boolean isTrue;
        if(taskAssignedToEmail.equals(userEmail) || taskAssignedByEmail.equals(userEmail)) {
            isTrue = true;
            if (isTrue) {
                task.setStatus(Status.valueOf(updateStatusRequest.getStatus()));
                taskRepository.save(task);
                return "Task is updated Successfully";
            }
        }
        return null;
    }

    public UserTasksResponse getTaskOfUser(String token){

       var jwt = token.substring(7);
       String  userEmail = jwtService.extractUserName(jwt);
       List<Task> allTasks = taskRepository.findByAssignedTo(userEmail);
       UserTasksResponse userTasksResponse = new UserTasksResponse();
       userTasksResponse.setTasks(allTasks);
       return userTasksResponse;
    }
}
