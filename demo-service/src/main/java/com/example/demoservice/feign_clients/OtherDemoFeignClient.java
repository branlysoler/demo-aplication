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

@FeignClient(name = "otherdemo-service")
public interface OtherDemoFeignClient {

    @PostMapping("/otherdemo-service/suplies/create")
    public ResponseEntity<?> create(@RequestBody SuplyDTO suplyDTO);

    @PutMapping("/otherdemo-service/suplies/update")
    public ResponseEntity<?> update(@RequestBody SuplyDTO suplyDTO);

    @GetMapping("/otherdemo-service/suplies/findAll")
    public ResponseEntity<?> findAll(Pageable pageable);

    @GetMapping("/otherdemo-service/suplies/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id);

    @GetMapping("/otherdemo-service/suplies/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name);

    @DeleteMapping("/otherdemo-service/suplies/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id);

    @DeleteMapping("/otherdemo-service/suplies/deleteAll")
    public ResponseEntity<?> deleteAll();
    
}