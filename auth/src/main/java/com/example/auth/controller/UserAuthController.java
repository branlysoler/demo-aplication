package com.example.auth.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.UserAuthDto;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.service.UserAuthService;

@RestController
@RequestMapping("/auth/user")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    private static final Log LOGGER = LogFactory.getLog(UserAuthController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserAuthDto userAuthDto) {
        LOGGER.info("Create User: " + userAuthDto.toString());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userAuthService.create(userAuthDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validate_token(@RequestParam(name = "token") String token) {
        LOGGER.info("Validate Token: " + token);
        try {
            return ResponseEntity.ok(userAuthService.validateToken(token));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}