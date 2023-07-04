package com.example.otherdemoservice.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.otherdemoservice.dto.SuplyDTO;
import com.example.otherdemoservice.exception.ResourceNotFoundException;
import com.example.otherdemoservice.service.SuplyService;

@RestController
@RequestMapping("/otherdemo-service/suplies")
public class SuplyController {

    @Autowired
    private SuplyService suplyService;

    private static final Log LOGGER = LogFactory.getLog(SuplyController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SuplyDTO suplyDTO) {
        LOGGER.info("Create SUply: " + suplyDTO.toString());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(suplyService.create(suplyDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SuplyDTO suplyDTO) {
        LOGGER.info("Update SUply: " + suplyDTO.toString());
        try {
            return ResponseEntity.ok(suplyService.update(suplyDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable) {
        final List<SuplyDTO> suplyDTOs = suplyService.findAll(pageable);
        if (suplyDTOs.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(suplyDTOs);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return suplyService.findById(id).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return suplyService.findByName(name).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (suplyService.findById(id).isPresent()) {
            suplyService.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        suplyService.deleteAll();
        return ResponseEntity.ok().build();
    }

}