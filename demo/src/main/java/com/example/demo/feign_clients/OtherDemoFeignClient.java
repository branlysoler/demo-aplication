package com.example.demo.feign_clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.SuplyDTO;

@FeignClient(name = "otherdemo", url = "http://localhost:8081/api")
public interface OtherDemoFeignClient {

    @PostMapping("/suplies/create")
    public ResponseEntity<?> create(@RequestBody SuplyDTO suplyDTO);

    @PutMapping("/suplies/update")
    public ResponseEntity<?> update(@RequestBody SuplyDTO suplyDTO);

    @GetMapping("/suplies/findAll")
    public ResponseEntity<?> findAll(Pageable pageable);

    @GetMapping("/suplies/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id);

    @GetMapping("/suplies/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name);

    @DeleteMapping("/suplies/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id);

    @DeleteMapping("/suplies/deleteAll")
    public ResponseEntity<?> deleteAll();
    
}