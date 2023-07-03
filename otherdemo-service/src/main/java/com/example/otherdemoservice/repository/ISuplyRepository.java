package com.example.otherdemoservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otherdemoservice.entity.Suply;

public interface ISuplyRepository extends JpaRepository<Suply, Long>{

    Optional<Suply> findByName(String name);
    
}