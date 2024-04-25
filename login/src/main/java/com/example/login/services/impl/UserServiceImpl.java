package com.example.login.services.impl;

import com.example.login.dto.TokenRequest;
import com.example.login.dto.SignInResponse;
import com.example.login.dto.UserProfileResponse;
import com.example.login.entities.User;
import com.example.login.repository.UserRepository;
import com.example.login.services.JwtService;
import com.example.login.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   @Autowired
   private final UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
   @Autowired
   private JwtService jwtService;
   @Override
   public UserDetailsService userDetailsService() {
       return new UserDetailsService() {
           @Override
           public UserDetails loadUserByUsername(String username) {
               return userRepository.findByEmail(username)
                       .orElseThrow(() -> new UsernameNotFoundException("user not found "));
           }
       };
   }

    public SignInResponse getDetails(TokenRequest tokenRequest){

        String userEmail = jwtService.extractUserName(tokenRequest.getToken());

        User user = userRepository.findByEmail(userEmail).orElseThrow();
        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setFirstName(user.getFirstname());
        signInResponse.setLastName(user.getLastname());
        signInResponse.setEmail(user.getEmail());
        return signInResponse;
    }

    public UserProfileResponse getProfile(TokenRequest tokenRequest){

        String email = jwtService.extractUserName(tokenRequest.getToken());
        User user = userRepository.findByEmail(email).orElseThrow();
        Long id = user.getId();
        UserProfileResponse profileResponse = restTemplate.getForObject("http://localhost:8081/api/user/"+id, UserProfileResponse.class);
        return profileResponse;
    }
}
