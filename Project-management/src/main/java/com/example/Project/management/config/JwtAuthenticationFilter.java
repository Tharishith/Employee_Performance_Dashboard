package com.example.Project.management.config;

import com.example.Project.management.repository.ProjectRepository;
import com.example.Project.management.service.JwtService;
import com.example.Project.management.service.ProjectService;
import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  final JwtService jwtService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        //storing the token
        final String jwt;

        final String tokenId;

        final Long verificationId;

        //checking whether Header is empty or not
        if (StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader,"Bearer")) {
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);

        if( !(StringUtils.isEmpty(jwt)) && SecurityContextHolder.getContext().getAuthentication()==null) {
          // UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            //verificationId = projectService.projectDetails().
//
           if (jwtService.isTokenValidcheck(jwt)) {
             //creating an empty Security context
               Claims claims = jwtService.extractAllClaims(jwt);
               String email = claims.getSubject();

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                      email  , null, null
                );
                // These details may include the user's IP address, the session ID, or other information related to the web request.
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request,response);
    }
}


