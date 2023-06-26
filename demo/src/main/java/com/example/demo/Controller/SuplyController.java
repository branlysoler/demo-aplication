package com.example.demo.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

import feign.FeignException;

@RestController
@RequestMapping("api/suplies")
public class SuplyController {

    @Autowired
    private OtherDemoFeignClient otherDemoFeignClient;

    private static final Log LOGGER = LogFactory.getLog(SuplyController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SuplyDTO suplyDTO) {
        LOGGER.info("Create Suply: " + suplyDTO.toString());
        try {
            return otherDemoFeignClient.create(suplyDTO);
        } catch (FeignException.BadRequest e) {
            return e.responseHeaders().get("cause").stream().findFirst()
                    .map(cause -> ResponseEntity.badRequest().header("cause", cause).build())
                    .orElse(ResponseEntity.internalServerError().build());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SuplyDTO suplyDTO) {
        try {
            return otherDemoFeignClient.update(suplyDTO);
        } catch (FeignException.BadRequest e) {
            return e.responseHeaders().get("cause").stream().findFirst()
                    .map(cause -> ResponseEntity.badRequest().header("cause", cause).build())
                    .orElse(ResponseEntity.internalServerError().build());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return otherDemoFeignClient.findAll(pageable);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return otherDemoFeignClient.findById(id);
        } catch (FeignException.NotFound e) {
            return e.responseHeaders().get("cause").stream().findFirst()
                    .map(cause -> ResponseEntity.notFound().header("cause", cause).build())
                    .orElse(ResponseEntity.internalServerError().build());
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        try {
            return otherDemoFeignClient.findByName(name);
        } catch (FeignException.NotFound e) {
            return e.responseHeaders().get("cause").stream().findFirst()
                    .map(cause -> ResponseEntity.notFound().header("cause", cause).build())
                    .orElse(ResponseEntity.internalServerError().build());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return otherDemoFeignClient.deleteById(id);
        } catch (FeignException.NotFound e) {
            return e.responseHeaders().get("cause").stream().findFirst()
                    .map(cause -> ResponseEntity.notFound().header("cause", cause).build())
                    .orElse(ResponseEntity.internalServerError().build());
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        return otherDemoFeignClient.deleteAll();
    }

}