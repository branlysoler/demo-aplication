package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employment;

public interface IEmploymentRepository extends JpaRepository<Employment, Long>{

    Optional<Employment> findByName(String name);

    Optional<Employment> findByLevel(Integer level);

    Optional<Employment> findByNameAndLevel(String name, Integer level);
    
}