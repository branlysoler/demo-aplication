package com.example.demoservice.feign_clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demoservice.dto.SuplyDTO;

@FeignClient(name = "otherdemo")
public interface OtherDemoFeignClient {

    @PostMapping("/api/suplies/create")
    public ResponseEntity<?> create(@RequestBody SuplyDTO suplyDTO);

    @PutMapping("/api/suplies/update")
    public ResponseEntity<?> update(@RequestBody SuplyDTO suplyDTO);

    @GetMapping("/api/suplies/findAll")
    public ResponseEntity<?> findAll(Pageable pageable);

    @GetMapping("/api/suplies/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id);

    @GetMapping("/api/suplies/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name);

    @DeleteMapping("/api/suplies/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id);

    @DeleteMapping("/api/suplies/deleteAll")
    public ResponseEntity<?> deleteAll();
    
}