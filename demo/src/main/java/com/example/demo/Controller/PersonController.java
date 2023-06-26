package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.Entity.Person;
import com.example.demo.Service.PersonService;

@RestController
@RequestMapping("api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Person person){
        final Person personCreated = personService.create(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Person person){
        final Person personUpdate = personService.update(person);
        return ResponseEntity.ok(personUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(){
        personService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        final List<Person> listPerson = personService.findAll();
        return ResponseEntity.ok(listPerson);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Person> person = personService.findById(id);
        return ResponseEntity.ok(person);
    }
    
}