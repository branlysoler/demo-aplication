package com.example.otherdemo.controller;

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

import com.example.otherdemo.dto.SuplyDTO;
import com.example.otherdemo.service.SuplyService;
import com.example.otherdemo.util.Text;

@RestController
@RequestMapping("api/suplies")
public class SuplyController {

    @Autowired
    private SuplyService suplyService;

    private static final Log LOGGER = LogFactory.getLog(SuplyController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SuplyDTO suplyDTO) {
        LOGGER.info("Create SUply: " + suplyDTO.toString());
        Boolean verifyCreate = suplyService.verifyCreate(suplyDTO);
        if (verifyCreate)
            return ResponseEntity.status(HttpStatus.CREATED).body(suplyService.save(suplyDTO));
        else
            return ResponseEntity.badRequest().header(Text.CAUSE, Text.VERIFY_CREATE_SUPLY).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SuplyDTO suplyDTO) {
        LOGGER.info("Update SUply: " + suplyDTO.toString());
        Boolean verifyUpdate = suplyService.verifyUpdate(suplyDTO);
        if (verifyUpdate)
            return ResponseEntity.ok(suplyService.save(suplyDTO));
        else
            return ResponseEntity.badRequest().header(Text.CAUSE, Text.VERIFY_UPDATE_SUPLY).build();
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
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.ID_NOT_EXISTS).build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return suplyService.findByName(name).map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().header(Text.CAUSE, Text.NAME_NOT_EXISTS).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (suplyService.findById(id).isPresent()) {
            suplyService.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().header(Text.CAUSE, Text.ID_NOT_EXISTS).build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        suplyService.deleteAll();
        return ResponseEntity.ok().build();
    }

}