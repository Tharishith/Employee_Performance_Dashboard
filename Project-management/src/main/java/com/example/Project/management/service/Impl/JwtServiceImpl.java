package com.example.Project.management.service.Impl;


import com.example.Project.management.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {


    private final String SECRET_KEY = "d12f63a0d62b108fa1b9930b47cf0e78ae77e781177ed30a65c1b86b51b8558c";


    // private static final String SECRET_KEY = "yourSecretKeyHere";
    public boolean isTokenValidcheck(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            // Handle invalid token or signature
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimResolvers.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token) {
        Claims claims = extractAllClaims(token);
        return (!isTokenExpried(token));
    }

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private boolean isTokenExpried(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }
}
