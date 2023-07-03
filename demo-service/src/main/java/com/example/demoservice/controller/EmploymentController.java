package com.example.demoservice.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoservice.dto.EmploymentDTO;
import com.example.demoservice.exception.ResourceNotFoundException;
import com.example.demoservice.service.EmploymentService;

@RestController
@RequestMapping("demo-service/employments")
public class EmploymentController {

    @Autowired
    private EmploymentService employmentService;

    private static final Log LOGGER = LogFactory.getLog(EmploymentController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EmploymentDTO employmentDTO) {
        LOGGER.info("Create Employment: " + employmentDTO.toString());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(employmentService.create(employmentDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody EmploymentDTO employmentDTO) {
        LOGGER.info("Update Employment: " + employmentDTO.toString());
        try {
            return ResponseEntity.ok(employmentService.update(employmentDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable) {
        final List<EmploymentDTO> employmentDTOs = employmentService.findAll(pageable);
        if (employmentDTOs.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(employmentDTOs);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return employmentService.findById(id).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return employmentService.findByName(name).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByLevel/{level}")
    public ResponseEntity<?> findByName(@PathVariable Integer level) {
        return employmentService.findByLevel(level).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByNameAndLevel")
    public ResponseEntity<?> findByNameAndLevel(@RequestParam(required = true) String name,
            @RequestParam(required = true) Integer level) {
        return employmentService.findByNameAndLevel(name, level).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (employmentService.findById(id).isPresent()) {
            employmentService.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        employmentService.deleteAll();
        return ResponseEntity.ok().build();
    }

}