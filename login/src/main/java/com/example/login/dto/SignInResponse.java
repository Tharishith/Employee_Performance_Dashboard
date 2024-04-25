package com.example.login.dto;

import com.example.login.entities.Role;
import lombok.Data;

@Data
public class SignInResponse {
    private Long user_id;
    private String firstName;
    private String lastName;
    private String email;
}
