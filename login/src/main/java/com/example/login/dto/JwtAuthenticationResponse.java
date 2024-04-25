package com.example.login.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String firstName;
    private String lastName;
    private String email;
    private Long user_id;
    private String Authority;
    private  String token;
    private String refreshToken;
    private boolean status;
    private String statusMsg;
}
