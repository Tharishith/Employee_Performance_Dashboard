package com.example.employee_profile.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileRequest {
    private Long user_id;
    private String department_name;
    private String address;
    private String gender;
    private Long phone_no;
    private List<Long> skillId;
}
