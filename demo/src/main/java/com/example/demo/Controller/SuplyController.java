package com.example.demo.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

import com.example.demo.dto.SuplyDTO;
import com.example.demo.feign_clients.OtherDemoFeignClient;
import com.example.demo.util.Text;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("api/suplies")
public class SuplyController {

    @Autowired
    private OtherDemoFeignClient otherDemoFeignClient;

    private static final Log LOGGER = LogFactory.getLog(SuplyController.class);

    @CircuitBreaker(name = "suplyCB", fallbackMethod = "fallbackCreateOrUpdate")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SuplyDTO suplyDTO) {
        LOGGER.info("Create Suply: " + suplyDTO.toString());
        try {
            return otherDemoFeignClient.create(suplyDTO);
        } catch (FeignException.BadRequest e) {
            return ResponseEntity.badRequest().body(new String(e.responseBody().get().array()));
        }
    }

    @CircuitBreaker(name = "suplyCB", fallbackMethod = "fallbackCreateOrUpdate")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SuplyDTO suplyDTO) {
        try {
            return otherDemoFeignClient.update(suplyDTO);
        } catch (FeignException.BadRequest e) {
            return ResponseEntity.badRequest().body(new String(e.responseBody().get().array()));
        }
    }

    @CircuitBreaker(name = "suplyCB", fallbackMethod = "fallbackFindAll")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return otherDemoFeignClient.findAll(pageable);
    }

    @CircuitBreaker(name = "suplyCB", fallbackMethod = "fallbackFindById")
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return otherDemoFeignClient.findById(id);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CircuitBreaker(name = "suplyCB", fallbackMethod = "fallbackFindByName")
    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        try {
            return otherDemoFeignClient.findByName(name);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CircuitBreaker(name = "suplyCB", fallbackMethod = "fallbackDelete")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return otherDemoFeignClient.deleteById(id);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CircuitBreaker(name = "suplyCB", fallbackMethod = "fallbackDeleteAll")
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        return otherDemoFeignClient.deleteAll();
    }

    /***********************************/
    /* methods fallback circuitBreaker */
    /***********************************/
    public ResponseEntity<?> fallbackCreateOrUpdate(@RequestBody SuplyDTO suplyDTO, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Text.MICROSERVICE_NOT_AVAILABLE);
    }

    public ResponseEntity<?> fallbackFindAll(Pageable pageable, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Text.MICROSERVICE_NOT_AVAILABLE);
    }

    public ResponseEntity<?> fallbackFindById(@PathVariable Long id, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Text.MICROSERVICE_NOT_AVAILABLE);
    }

    public ResponseEntity<?> fallbackFindByName(@PathVariable String name, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Text.MICROSERVICE_NOT_AVAILABLE);
    }

    public ResponseEntity<?> fallbackDelete(@PathVariable Long id, RuntimeException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Text.MICROSERVICE_NOT_AVAILABLE);
    }

    public ResponseEntity<?> fallbackDeleteAll(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Text.MICROSERVICE_NOT_AVAILABLE);
    }

}