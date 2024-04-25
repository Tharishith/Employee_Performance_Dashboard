package com.example.employee_profile.service;

import com.example.employee_profile.dto.SkillRequest;
import com.example.employee_profile.dto.SkillResponse;
import org.springframework.stereotype.Service;


public interface SkillService {
    SkillResponse addSkill(SkillRequest skillRequest);

    String getSkillById(Long id);

    String deleteSkillById(Long id);
}
