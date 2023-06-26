package com.example.demo.controller;

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

import com.example.demo.dto.EmploymentDTO;
import com.example.demo.service.EmploymentService;
import com.example.demo.util.Text;

@RestController
@RequestMapping("api/employments")
public class EmploymentController {

    @Autowired
    private EmploymentService employmentService;

    private static final Log LOGGER = LogFactory.getLog(EmploymentController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EmploymentDTO employmentDTO) {
        LOGGER.info("Create Employment: " + employmentDTO.toString());
        Boolean verifyCreate = employmentService.verifyCreate(employmentDTO);
        if (verifyCreate)
            return ResponseEntity.status(HttpStatus.CREATED).body(employmentService.save(employmentDTO));
        else
            return ResponseEntity.badRequest().header(Text.CAUSE, Text.VERIFY_CREATE_EMPLOYMENT).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody EmploymentDTO employmentDTO) {
        LOGGER.info("Update Employment: " + employmentDTO.toString());
        Boolean verifyUpdate = employmentService.verifyUpdate(employmentDTO);
        if (verifyUpdate)
            return ResponseEntity.ok(employmentService.save(employmentDTO));
        else
            return ResponseEntity.badRequest().header(Text.CAUSE, Text.VERIFY_UPDATE_EMPLOYMENT).build();
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
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.ID_NOT_EXISTS).build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return employmentService.findByName(name).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.NAME_NOT_EXISTS).build());
    }

    @GetMapping("/findByLevel/{level}")
    public ResponseEntity<?> findByName(@PathVariable Integer level) {
        return employmentService.findByLevel(level).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.LEVEL_NOT_EXISTS).build());
    }

    @GetMapping("/findByNameAndLevel")
    public ResponseEntity<?> findByNameAndLevel(@RequestParam(required = true) String name,
            @RequestParam(required = true) Integer level) {
        return employmentService.findByNameAndLevel(name, level).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.NAME_AND_LEVEL_NOT_EXISTS).build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (employmentService.findById(id).isPresent()) {
            employmentService.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().header(Text.CAUSE, Text.ID_NOT_EXISTS).build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        employmentService.deleteAll();
        return ResponseEntity.ok().build();
    }

}