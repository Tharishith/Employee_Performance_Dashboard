package com.example.employee_profile.service;

import com.example.employee_profile.dto.UserProfileRequest;
import com.example.employee_profile.dto.UserProfileResponse;
import com.example.employee_profile.dto.UserResponse;
import com.example.employee_profile.dto.UserTokenRequest;


public interface UserProfileService {
    String createUserProfile(UserProfileRequest userProfileRequest);

    String updateUserProfile(UserProfileRequest userProfileRequest);

    String deleteUserProfileById(Long id);

    UserProfileResponse getProfileById(Long id);

    UserResponse getProfile(Long id);


}
