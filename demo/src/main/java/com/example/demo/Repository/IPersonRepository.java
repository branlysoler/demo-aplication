package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Person;

public interface IPersonRepository extends JpaRepository<Person, Long>{
    
}