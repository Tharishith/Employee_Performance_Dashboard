package com.example.login.dto;

import lombok.Data;

@Data
public class SignUpResponse {

    private Long User_id;

    private boolean status;

    private String StatusMessage;
    private String firstName;

    private String lastName;

    private String email;


}
