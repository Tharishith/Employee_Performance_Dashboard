package com.example.login.repository;

import com.example.login.entities.Role;
import com.example.login.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    Optional<User> findById(Long id);

    List<User> findAll();

    boolean existsByEmail(String email);
}
