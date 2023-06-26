package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Person;
import com.example.demo.Repository.IPersonRepository;

@Service
public class PersonService {

    @Autowired
    private IPersonRepository iPersonRepository;

    public Person create(Person person){
        return iPersonRepository.save(person);
    }

    public Person update(Person person){
        return iPersonRepository.save(person);
    }

    public void deleteById(Long id){
        iPersonRepository.deleteById(id);
    }

    public void deleteAll(){
        iPersonRepository.deleteAll();
    }

    public List<Person> findAll(){
        return iPersonRepository.findAll();
    }

    public Optional<Person> findById(Long id){
        return iPersonRepository.findById(id);
    }
    
}