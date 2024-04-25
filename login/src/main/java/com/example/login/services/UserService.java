package com.example.login.services;

import com.example.login.dto.TokenRequest;
import com.example.login.dto.SignInResponse;
import com.example.login.dto.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    SignInResponse getDetails(TokenRequest tokenRequest);

    UserProfileResponse getProfile(TokenRequest tokenRequest);

}
