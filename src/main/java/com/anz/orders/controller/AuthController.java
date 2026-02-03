package com.anz.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anz.orders.model.AuthRequest;
import com.anz.orders.service.JwtUtilityService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final JwtUtilityService jwtUtil;

    public AuthController(JwtUtilityService jwtUtil) {
        this.jwtUtil = jwtUtil; 
    }

    @PostMapping("/getToken")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(jwtUtil.generateToken(request.getUsername(), request.getPassword()));

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }


    @Data
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
    }
}
