package com.example.employee_profile.repository;

import com.example.employee_profile.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
    Skill findBySkillName(String skillName);
}
