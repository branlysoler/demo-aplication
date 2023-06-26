package com.example.otherdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otherdemo.entity.Suply;

public interface ISuplyRepository extends JpaRepository<Suply, Long>{

    Optional<Suply> findByName(String name);
    
}