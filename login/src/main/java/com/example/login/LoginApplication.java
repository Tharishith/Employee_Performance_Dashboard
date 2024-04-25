package com.example.login;

import com.example.login.entities.Role;
import com.example.login.entities.User;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class LoginApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

	public WebMvcConfigurer corsConfiguer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200");
			}
		};
	}


	public  void run(String... args){
		List<User>adminAccount = userRepository.findByRole(Role.ADMIN);
		if(adminAccount.isEmpty()){
			User user = new User();

			user.setEmail("admin@gmail.com");
			user.setFirstname("DEFAULT");
			user.setLastname("ADMIN@123");
			user.setRole(Role.ADMIN);
			user.setPassword( new BCryptPasswordEncoder().encode("ADMIN@123"));
			userRepository.save(user);
		}
	}
}
