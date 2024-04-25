package com.example.employee_profile.service.impl;

import com.example.employee_profile.dto.SkillRequest;
import com.example.employee_profile.dto.SkillResponse;
import com.example.employee_profile.entities.Skill;
import com.example.employee_profile.repository.SkillRepository;
import com.example.employee_profile.service.SkillService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository skillRepository;
    public SkillResponse addSkill(SkillRequest skillRequest){

        String skillName = skillRequest.getName().toLowerCase();
        Skill skill = new Skill();
        if(skillRepository.findBySkillName(skillName) == null) {
            skill.setSkillName(skillRequest.getName().toLowerCase());
            Skill skill1 =skillRepository.save(skill);
            skill.setSkill_id(skill1.getSkill_id());
            return MapToResponse(skill);
        }
        else{
             Skill presentSkill = skillRepository.findBySkillName(skillName);
             SkillResponse skillResponse = new SkillResponse();
             skillResponse.setId(presentSkill.getSkill_id());
             skillResponse.setSkill_name(presentSkill.getSkillName());
             return skillResponse;
        }
    }

    public String getSkillById(Long id){
        Skill skill = skillRepository.findById(id).get();
        return skill.getSkillName();
    }

    public SkillResponse MapToResponse(Skill skill){
        SkillResponse  skillResponse = new SkillResponse();
        skillResponse.setId(skill.getSkill_id());
        skillResponse.setSkill_name(skill.getSkillName());
        return skillResponse;
    }

    public String deleteSkillById(Long id){

      Skill skill =  skillRepository.findById(id).get();
      if(!skill.isDeleted()){
          skill.setDeleted(true);
          skillRepository.save(skill);
      }
      return "Skill has removed";
    }
}
