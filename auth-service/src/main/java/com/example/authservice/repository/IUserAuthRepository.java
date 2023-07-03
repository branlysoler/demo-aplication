package com.example.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authservice.entity.UserAuth;

public interface IUserAuthRepository extends JpaRepository<UserAuth, Long>{

    Optional<UserAuth> findByEmail(String email);
    
}