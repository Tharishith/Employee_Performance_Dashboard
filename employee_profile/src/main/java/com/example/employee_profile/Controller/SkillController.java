package com.example.employee_profile.Controller;

import com.example.employee_profile.dto.SkillRequest;
import com.example.employee_profile.dto.SkillResponse;
import com.example.employee_profile.repository.SkillRepository;
import com.example.employee_profile.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;
//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/add")
    public ResponseEntity<SkillResponse> addSkill(@RequestBody SkillRequest skillRequest){
        return ResponseEntity.ok(skillService.addSkill(skillRequest));
    }


    @GetMapping("{id}")
    public ResponseEntity<String> getSkillById(@PathVariable Long id){
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<String> deleteSkillById(@PathVariable Long id){
        return ResponseEntity.ok(skillService.deleteSkillById(id));
    }



}
