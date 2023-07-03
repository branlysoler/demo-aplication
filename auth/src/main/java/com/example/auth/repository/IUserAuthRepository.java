package com.example.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.UserAuth;

public interface IUserAuthRepository extends JpaRepository<UserAuth, Long>{

    Optional<UserAuth> findByEmail(String email);
    
}