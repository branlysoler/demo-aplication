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
import org.springframework.web.bind.annotation.RestController;

import com.example.demoservice.dto.CategoryDTO;
import com.example.demoservice.exception.ResourceNotFoundException;
import com.example.demoservice.service.CategoryService;

@RestController
@RequestMapping("/demo-service/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final Log LOGGER = LogFactory.getLog(CategoryController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDto) {
        LOGGER.info("Create Category: " + categoryDto.toString());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CategoryDTO categoryDto) {
        LOGGER.info("Update Category: " + categoryDto.toString());
        try {
            return ResponseEntity.ok(categoryService.update(categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable) {
        final List<CategoryDTO> categoryDTOs = categoryService.findAll(pageable);
        if (categoryDTOs.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return categoryService.findById(id).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return categoryService.findByName(name).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        categoryService.deleteAll();
        return ResponseEntity.ok().build();
    }

}