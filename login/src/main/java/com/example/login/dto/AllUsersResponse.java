package com.example.login.dto;

import lombok.Data;

@Data
public class AllUsersResponse {
    private Long user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String authority;
}
