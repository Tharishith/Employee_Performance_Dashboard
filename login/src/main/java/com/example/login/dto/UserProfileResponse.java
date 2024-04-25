package com.example.login.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileResponse {
    private String department_name;
    private String address;
    private String gender;
    private Long phone_no;
    private List<String> skills;
}
