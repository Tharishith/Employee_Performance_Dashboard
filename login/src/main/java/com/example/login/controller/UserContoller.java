package com.example.login.controller;

import com.example.login.dto.TokenRequest;
import com.example.login.dto.SignInResponse;
import com.example.login.dto.UserProfileResponse;
import com.example.login.services.AuthenticationService;
import com.example.login.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserContoller {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("This is User");
    }


    @GetMapping("/details")
    public ResponseEntity<SignInResponse> getDetails(@RequestBody TokenRequest tokenRequest){
        return ResponseEntity.ok(userService.getDetails(tokenRequest));
    }

   @GetMapping("/profile")
   public ResponseEntity<UserProfileResponse> getProfile(@RequestBody TokenRequest tokenRequest){
        return ResponseEntity.ok(userService.getProfile(tokenRequest));
   }

}
