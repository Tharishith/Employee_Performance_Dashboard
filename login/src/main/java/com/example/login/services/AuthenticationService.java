package com.example.login.services;

import com.example.login.dto.*;

import java.util.ArrayList;
import java.util.List;

public interface AuthenticationService  {

    //User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(TokenRequest tokenRequest);

    SignUpResponse signup(SignUpRequest signUpRequest);

    SignUpResponse NewAdmin(SignUpRequest signUpRequest);

    SignUpResponse NewManager(SignUpRequest signUpRequest);

    SignUpResponse NewUser(SignUpRequest signUpRequest);

     String updateRoleByAdmin(Long id,AdminRequest role);

     String deleteUser(Long id);

     List<AllUsersResponse> GetAllUsers(String token);

     String updateRoleByManager(Long id, ManagerRequest role);

    Boolean checkUser( EmailRequest request);

    List<AllUsersResponse> getUsers(String token);


    //  UserProfileResponse getDetails(RefreshTokenRequest refreshTokenRequest);
}
