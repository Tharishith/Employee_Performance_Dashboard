package com.example.login.controller;

import com.example.login.dto.*;
import com.example.login.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class MangerController {

    @Autowired
    private AuthenticationService authenticationService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi Manager");
    }

    @PostMapping("/new/user")
    public ResponseEntity<SignUpResponse> NewUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.NewUser(signUpRequest));
    }

    @PutMapping("/update/role/{id}")
    public ResponseEntity<String> UpdateRole(@PathVariable Long id, @RequestBody ManagerRequest role){
        return ResponseEntity.ok(authenticationService.updateRoleByManager(id,role));

    }

    @GetMapping("all/users")
    public ResponseEntity<List<AllUsersResponse>>users(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(authenticationService.getUsers(token));
    }

}
