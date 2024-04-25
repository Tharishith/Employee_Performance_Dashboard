package com.example.employee_profile.Controller;

import com.example.employee_profile.dto.UserProfileRequest;

import com.example.employee_profile.dto.UserProfileResponse;
import com.example.employee_profile.dto.UserResponse;
import com.example.employee_profile.dto.UserTokenRequest;
import com.example.employee_profile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {
      @Autowired
      private UserProfileService userProfileService;
//      @CrossOrigin(origins = "http://localhost:4200")
      @PostMapping("/profile")
      public ResponseEntity<String> createUserProfile(@RequestBody UserProfileRequest userProfileRequest){
          return ResponseEntity.ok(userProfileService.createUserProfile(userProfileRequest));
      }
//      @CrossOrigin(origins = "http://localhost:4200")
      @PutMapping("/update/profile")
      public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileRequest userProfileRequest){
            return  ResponseEntity.ok(userProfileService.updateUserProfile(userProfileRequest));
      }

      @DeleteMapping("/delete/profile/{id}")
      public ResponseEntity<String> deleteUserProfile(@PathVariable Long id){
            return  ResponseEntity.ok(userProfileService.deleteUserProfileById(id));
      }
      @CrossOrigin(origins = "http://localhost:4200")
      @GetMapping("/{id}")
      public ResponseEntity<UserResponse> getProfileByToken(@PathVariable Long id){
            return ResponseEntity.ok(userProfileService.getProfile(id));
      }

}
