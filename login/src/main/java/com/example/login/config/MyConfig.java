package com.example.login.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements  WebMvcConfigurer{

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("***") // Specify the endpoint for which CORS should be enabled
//                .allowedOrigins("http://localhost:4200") // Specify the origin from which requests are allowed
//                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Specify the HTTP methods allowed
//    }
}


