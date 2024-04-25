package com.example.login.services.impl;

import com.example.login.dto.*;
import com.example.login.entities.Role;
import com.example.login.entities.User;
import com.example.login.repository.UserRepository;
import com.example.login.services.AuthenticationService;
import com.example.login.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;

    private Long generated_id;

    public SignUpResponse signup(SignUpRequest signUpRequest) {

        SignUpResponse response=null;
        try {

            if(userRepository.existsByEmail(signUpRequest.getEmail())){
                throw new RuntimeException("Email alredy exists");
            }
            User user = new User();
            user.setFirstname(signUpRequest.getFirstName());
            user.setLastname(signUpRequest.getLastName());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setRole(Role.USER);
            User saved_user = userRepository.save(user);
            generated_id = saved_user.getId();
            response=new SignUpResponse();
            response.setUser_id(generated_id);
            response.setStatus(true);
            response.setStatusMessage("Success");
            response.setEmail(user.getEmail());
            response.setFirstName(signUpRequest.getFirstName());
            response.setLastName(signUpRequest.getLastName());

        }
        catch (Exception e ){
            log.error("Somthing went wrong during signup {}",e.getMessage());
            response=new SignUpResponse();
            response.setStatus(false);
            response.setStatusMessage(e.getMessage());
        }
        return response;

    }


    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {



             JwtAuthenticationResponse response= null;
             try{
                 if(!userRepository.existsByEmail(signInRequest.getEmail())){
                     throw new RuntimeException("User is not registered");
                 }
                 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
                 var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                         () -> new IllegalArgumentException("Invalid email or password"));
                 var jwt = jwtService.generateToken(user);
                 var refershToken = jwtService.generateRefreshToken(new HashMap<>(), user);
                 var Authority = user.getAuthorities();
                 Long user_id = user.getId();
                 response = new JwtAuthenticationResponse();
                 response.setToken(jwt);
                 response.setUser_id(user_id);
                 response.setAuthority(Authority.toString());
                 response.setRefreshToken(refershToken);
                 response.setFirstName(user.getFirstname());
                 response.setLastName(user.getLastname());
                 response.setEmail(user.getEmail());
                 response.setStatus(true);
                 response.setStatusMsg("Success");
             }
             catch (Exception e){
                 log.error("Something went wrong during login {}",e.getMessage());
                 response=new JwtAuthenticationResponse();
                 response.setStatus(false);
                 response.setStatusMsg(e.getMessage());
             }

        return response;
    }

    public JwtAuthenticationResponse refreshToken(TokenRequest tokenRequest) {

        String userEmail = jwtService.extractUserName(tokenRequest.getToken());

        User user = userRepository.findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(tokenRequest.getToken(), user)) {
                var jwt = jwtService.generateToken(user);
                JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
                jwtAuthenticationResponse.setToken(jwt);
                jwtAuthenticationResponse.setRefreshToken(tokenRequest.getToken());
                jwtAuthenticationResponse.setFirstName(user.getFirstname());
                jwtAuthenticationResponse.setLastName(user.getLastname());
                jwtAuthenticationResponse.setEmail(user.getEmail());
                return jwtAuthenticationResponse;
            }
            return null;
    }
    public SignUpResponse NewAdmin(SignUpRequest signUpRequest){
        User user = new User();
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastname(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.ADMIN);
        User person = userRepository.save(user);
        Long id = person.getId();
        return ResponseBody(signUpRequest,id);
    }

    public String updateRoleByAdmin(Long id,AdminRequest role ){
        User user = userRepository.findById(id).get();
        Role newrole = Role.valueOf(role.getRole());
        user.setRole(newrole);
        userRepository.save(user);
        return  "User is updated successfully";
    }

    public String updateRoleByManager(Long id, ManagerRequest role){
        User user = userRepository.findById(id).get();
        Role newrole = Role.valueOf(role.getRole());
        user.setRole(newrole);
        userRepository.save(user);
        return  "User is updated successfully";
    }
    @Override
    public SignUpResponse NewManager(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastname(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.Manager);

        User person = userRepository.save(user);
        Long id = person.getId();
        return ResponseBody(signUpRequest,id);
    }

   public SignUpResponse NewUser(SignUpRequest signUpRequest){
        User user = new User();
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastname(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        User person = userRepository.save(user);
        Long id =  person.getId();
       return ResponseBody(signUpRequest,id);
   }

   public String deleteUser(Long id){
         userRepository.deleteById(id);
         return "User is successfully";
   }
//   public UserProfileResponse getDetails(RefreshTokenRequest refreshTokenRequest){
//
//       String email = jwtService.extractUserName(refreshTokenRequest.getToken());
//       User user = userRepository.findByEmail(email).get();
//
//       Object obj = restTemplate.getForObject("http://localhost:8081/api/user/" + user.getId(), Object.class);
//       logger.info("{}", obj);
//       return MapUserProfile(user, obj);
//
//    }
    public List<AllUsersResponse> GetAllUsers(String token){
        List<AllUsersResponse> AllUsers = new ArrayList<>();
        List<User> members = userRepository.findAll();
        for( User user : members){
            AllUsers.add(MapToAllusers(user));
        }

        return  AllUsers;
    }
    public AllUsersResponse MapToAllusers(User user){
        AllUsersResponse response = new AllUsersResponse();
        response.setFirstName(user.getFirstname());
        response.setLastName(user.getLastname());
        response.setEmail(user.getEmail());
        response.setUser_id(user.getId());
        response.setAuthority(String.valueOf(user.getRole()));
        return response;
    }
    public SignUpResponse ResponseBody(SignUpRequest signUpRequest,Long id){
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setUser_id(id);
        signUpResponse.setStatus(true);
        signUpResponse.setStatusMessage("Success");
        signUpResponse.setFirstName(signUpRequest.getFirstName());
        signUpResponse.setLastName(signUpRequest.getLastName());
        signUpResponse.setEmail(signUpRequest.getEmail());
        return signUpResponse;
    }


    public SignInResponse MapResponseToSignIn(User user){
        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setUser_id(user.getId());
        signInResponse.setFirstName(user.getFirstname());
        signInResponse.setLastName(user.getLastname());
        signInResponse.setEmail(user.getEmail());
        return signInResponse;
    }

    public Boolean checkUser(EmailRequest request){
        boolean UserPresent = userRepository.existsByEmail(request.getEmail());
        if (UserPresent) {
            System.out.println("In side if");
            return true;

        }
        else return false;
    }

    public List<AllUsersResponse> getUsers(String token) {
        List<AllUsersResponse> OnlyUsers = new ArrayList<>();
        List<User> members = userRepository.findAll();
        for (User user : members) {
            if (user.getRole() == Role.USER) {
                OnlyUsers.add(MapToAllusers(user));
            }
        }
        return OnlyUsers;
    }
}