package com.example.employee_profile.service.impl;

import com.example.employee_profile.dto.UserProfileRequest;
import com.example.employee_profile.dto.UserProfileResponse;
import com.example.employee_profile.dto.UserResponse;
import com.example.employee_profile.dto.UserTokenRequest;
import com.example.employee_profile.entities.Skill;
import com.example.employee_profile.entities.User_profile;
import com.example.employee_profile.repository.SkillRepository;
import com.example.employee_profile.repository.UserProfileRepository;
import com.example.employee_profile.service.UserProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {



    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    public String createUserProfile(UserProfileRequest userProfileRequest){
        User_profile userProfile = new User_profile();
        userProfile.setUser_id(userProfileRequest.getUser_id());
        userProfile.setAddress(userProfileRequest.getAddress());
        userProfile.setGender(userProfileRequest.getGender());
        userProfile.setDepartment_name(userProfileRequest.getDepartment_name());
        userProfile.setPhone_no(userProfileRequest.getPhone_no());
        List<Skill> userSkills = new ArrayList<>();
        if(!userProfileRequest.getSkillId().isEmpty()) {
            List<Long> skillId = userProfileRequest.getSkillId();
            for (Long id : skillId) {
               userSkills.add(skillRepository.findById(id).get());
            }
            userProfile.setSkills(userSkills);
        }
        userProfileRepository.save(userProfile);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Profile is created successfully");

        // Serialize the JSON object to a string
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(jsonResponse);
        } catch (JsonProcessingException e) {
            // Handle serialization error
            jsonString = "{\"error\": \"Failed to serialize response\"}";
        }

        return jsonString;
    }
     public String updateUserProfile(UserProfileRequest userProfileRequest){
        User_profile userProfile = new User_profile();
        userProfile.setUser_id(userProfileRequest.getUser_id());
        userProfile.setAddress(userProfileRequest.getAddress());
        userProfile.setGender(userProfileRequest.getGender());
        userProfile.setDepartment_name(userProfileRequest.getDepartment_name());
        userProfile.setPhone_no(userProfileRequest.getPhone_no());
        List<Skill> userSkills = new ArrayList<>();
        if(!userProfileRequest.getSkillId().isEmpty()) {
            List<Long> skillId = userProfileRequest.getSkillId();
            for (Long id : skillId) {
                userSkills.add(skillRepository.findById(id).get());
            }
            userProfile.setSkills(userSkills);
        }
        userProfileRepository.save(userProfile);
        return "Profile is updated successfully";
    }
    public String deleteUserProfileById(Long id){
        userProfileRepository.deleteById(id);
        return "Profile is deleted Successfully";
    }

    public UserProfileResponse getProfileById(Long id){
        User_profile userProfile = userProfileRepository.findById(id).get();
        return MapToUserResponse(userProfile);
    }

   public UserProfileResponse MapToUserResponse(User_profile userProfile){
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setAddress(userProfile.getAddress());
        userProfileResponse.setGender(userProfile.getGender());
        userProfileResponse.setDepartment_name(userProfile.getDepartment_name());
        userProfileResponse.setPhone_no(userProfile.getPhone_no());
        List<Skill> userSkills = userProfile.getSkills();
        List<String> allSkill = new ArrayList<>();
        if(!userSkills.isEmpty()) {
            for (Skill sk : userSkills){
                allSkill.add(sk.getSkillName());
            }
        }
        userProfileResponse.setSkills(allSkill);
        return userProfileResponse;
   }

   public UserResponse getProfile(Long id){

       User_profile userProfile = userProfileRepository.findById(id).get();
       return MapUserProfile(userProfile);
   }

   public UserResponse MapUserProfile(User_profile userProfile){
        UserResponse userResponse = new UserResponse();
        userResponse.setDepartment_name(userProfile.getDepartment_name());
        List<String>userSkill = new ArrayList<>();
        for(Skill skill : userProfile.getSkills()){
            if(!skill.isDeleted()) {
                userSkill.add(skill.getSkillName());
            }
        }
        userResponse.setSkills(userSkill);
        userResponse.setPhone_no(userProfile.getPhone_no());
        userResponse.setGender(userProfile.getGender());
        userResponse.setAddress(userProfile.getAddress());
        return  userResponse;
   }

}
