package com.example.demoservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoservice.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByName(String name);
    
}