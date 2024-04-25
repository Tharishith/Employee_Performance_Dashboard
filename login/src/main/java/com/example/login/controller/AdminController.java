package com.example.login.controller;

import com.example.login.dto.*;
import com.example.login.repository.UserRepository;
import com.example.login.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationService authenticationService;


    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("This is  Admin");
    }

    @PostMapping("/new/admin")
    public ResponseEntity<SignUpResponse> NewAdmin(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.NewAdmin(signUpRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllUsersResponse>> getAllUsers(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(authenticationService.GetAllUsers(token));
    }

    @PostMapping("/new/manager")
    public ResponseEntity<SignUpResponse> NewManager(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.NewManager(signUpRequest));
    }

    @PutMapping("/update/role/{id}")
    public ResponseEntity<String> UpdateRole(@PathVariable Long id,@RequestBody AdminRequest role){
        return ResponseEntity.ok(authenticationService.updateRoleByAdmin(id,role));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteUser(@PathVariable Long id){
        return  ResponseEntity.ok(authenticationService.deleteUser(id));
    }

    @PostMapping("new/user")
    public ResponseEntity<SignUpResponse> createUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.NewUser(signUpRequest));
    }


}
