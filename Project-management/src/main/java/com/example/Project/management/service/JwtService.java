package com.example.Project.management.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractId(String token);


    boolean isTokenValid(String token);

    Claims extractAllClaims(String token);

     <T> T extractClaim(String token, Function<Claims,T> claimResolvers);

   // String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails);

    String extractUserName(String token);

    //boolean verifyToken(String token);

    boolean isTokenValidcheck(String jwt);
}
