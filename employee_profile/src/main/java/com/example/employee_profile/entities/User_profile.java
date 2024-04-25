package com.example.employee_profile.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import lombok.Data;


import java.util.List;


@Data
@Entity
public class User_profile {
   @Id
   private Long user_id;
   private String department_name;
   @Column(nullable = false)
   private String address;
   @Column(nullable = false)
   private  String Gender;
   @Column(nullable = false)
   private Long phone_no;
   @ManyToMany
   private List<Skill> skills;

}
