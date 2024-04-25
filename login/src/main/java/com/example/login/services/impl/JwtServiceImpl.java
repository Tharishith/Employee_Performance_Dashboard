package com.example.login.services.impl;

import com.example.login.entities.Role;
import com.example.login.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    Map<String,Object> myMap = new HashMap<>();
    private  final String SECRET_KEY = "d12f63a0d62b108fa1b9930b47cf0e78ae77e781177ed30a65c1b86b51b8558c";

    //creating a token
    public String generateToken(UserDetails userDetails){




        return Jwts.builder().setSubject(userDetails.getUsername())
                .claim("Authority",userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
   //extracting the Claim
    private <T> T extractClaim(String token, Function<Claims,T>claimResolvers){
        final Claims claims = extractAllClaims(token);
        return claimResolvers.apply(claims);
    }
   //extracting all the Claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }
   //extracting the username(we are using emailId)
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpried(token));
    }

    private boolean isTokenExpried(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    private Key getSignKey() {
      byte[] key = Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(key);
    }


}
