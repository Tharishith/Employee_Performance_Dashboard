package com.example.employee_profile.repository;

import com.example.employee_profile.entities.User_profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<User_profile,Long> {
}
