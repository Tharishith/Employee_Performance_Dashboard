package com.example.Project.management.controller;

import com.example.Project.management.dto.*;
import com.example.Project.management.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/project")
public class ProjectController {

     @Autowired
     private ProjectService projectService;

     @PostMapping("/create")
     public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest){
        return ResponseEntity.ok(projectService.createProject(projectRequest));
     }


     @GetMapping("/details")
     public ResponseEntity<List<CompleteProjectResponse>> CompleteProject(@RequestHeader("Authorization") String token){
          return ResponseEntity.ok(projectService.completeDetails(token));
     }

     @PutMapping("/details/{id}")
     public ResponseEntity<String> updateProjectDetails(@PathVariable Long id,@RequestBody ProjectRequest projectRequest) {
          return ResponseEntity.ok(projectService.updateProjectDetails(id,projectRequest));
     }

     @PutMapping("/status/{id}")
     public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest updateStatusRequest){
          return ResponseEntity.ok(projectService.updateStatus(id,updateStatusRequest));
     }

     @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deleteProjectById(@PathVariable Long id){
          return ResponseEntity.ok(projectService.deleteProject(id));
     }
     @GetMapping("/all/projects")
     public ResponseEntity<List<CompleteProjectResponse>> allProjects(){
          return ResponseEntity.ok(projectService.getAllProjects());
     }

     @GetMapping("all/tasks/{id}")
     public ResponseEntity<ListofTasks> getTaskId(@PathVariable Long id){
          return ResponseEntity.ok(projectService.getTasks(id));
     }
}
