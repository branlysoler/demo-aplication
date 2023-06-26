package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.EmploymentDTO;
import com.example.demo.service.CategoryService;
import com.example.demo.util.Text;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final Log LOGGER = LogFactory.getLog(CategoryController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDTO) {
        LOGGER.info("Create Category: " + categoryDTO.toString());
        Boolean verifyCreate = categoryService.verifyCreate(categoryDTO);
        if (verifyCreate)
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDTO));
        else
            return ResponseEntity.badRequest().header(Text.CAUSE, Text.VERIFY_CREATE_CATEGORY).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CategoryDTO categoryDTO) {
        LOGGER.info("Update SUply: " + categoryDTO.toString());
        Optional<CategoryDTO> optVerifyUpdate = categoryService.verifyUpdate(categoryDTO);
        if (optVerifyUpdate.isPresent()) {
            List<EmploymentDTO> employmentDTOs = optVerifyUpdate.get().getEmployments();
            categoryDTO.setEmployments(employmentDTOs);
            return ResponseEntity.ok(categoryService.save(categoryDTO));
        }
        else
            return ResponseEntity.badRequest().header(Text.CAUSE, Text.VERIFY_UPDATE_CATEGORY).build();
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
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.ID_NOT_EXISTS).build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return categoryService.findByName(name).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.NAME_NOT_EXISTS).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().header(Text.CAUSE, Text.ID_NOT_EXISTS).build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        categoryService.deleteAll();
        return ResponseEntity.ok().build();
    }

}