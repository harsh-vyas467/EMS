package com.practice.springboot.ems.repository;

import com.practice.springboot.ems.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);
}
