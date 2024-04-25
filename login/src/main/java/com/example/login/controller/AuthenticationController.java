package com.example.login.controller;

import com.example.login.dto.*;
import com.example.login.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest signUpRequest){
    return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }
//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody TokenRequest tokenRequest){
      return ResponseEntity.ok(authenticationService.refreshToken(tokenRequest));
    }

    @PostMapping("/check/email")
    public ResponseEntity<Boolean> checkUser(@RequestBody  EmailRequest request){
        return ResponseEntity.ok(authenticationService.checkUser(request));
    }
}
