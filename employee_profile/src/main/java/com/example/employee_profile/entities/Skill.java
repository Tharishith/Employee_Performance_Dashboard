package com.example.employee_profile.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skill_id;
    @Column(nullable = false,unique = true,name = "skillName")
    private String skillName;
    private boolean isDeleted;

    @PrePersist
    private void prePersist(){
        isDeleted = Boolean.FALSE;
    }



}
