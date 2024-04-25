package com.example.login.dto;

import lombok.Data;

@Data
public class SkillResponse {

    private Long skill_id;
    private String skill_Name;
    private boolean isDeleted;
}
