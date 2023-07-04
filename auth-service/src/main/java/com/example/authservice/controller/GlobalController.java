package com.example.authservice.controller;

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

import com.example.authservice.dto.AuthCredentials;
import com.example.authservice.dto.UserAuthDto;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.service.UserAuthService;

@RestController
@RequestMapping("/auth-service/global")
public class GlobalController {

    @Autowired
    private UserAuthService userAuthService;

    private static final Log LOGGER = LogFactory.getLog(GlobalController.class);

    @PostMapping("/create-first")
    public ResponseEntity<?> createFirst(@RequestBody UserAuthDto userAuthDto) {
        LOGGER.info("Create User: " + userAuthDto.toString());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userAuthService.createFirst(userAuthDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentials authCredentials) {
        LOGGER.info("Login for Credentials: " + authCredentials.toString());
        try {
            return ResponseEntity.ok(userAuthService.login(authCredentials));
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