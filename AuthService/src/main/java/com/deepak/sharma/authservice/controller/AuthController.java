package com.deepak.sharma.authservice.controller;

import com.deepak.sharma.authservice.dto.request.SignupRequest;
import com.deepak.sharma.authservice.service.AuthService;
import com.deepak.sharma.authservice.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/") // http://localhost:8081/v1/auth/
    public String home() {
        return "Welcome to Auth Service!";
    }

    @PostMapping("/signup") // http://localhost:8081/v1/auth/signup
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authService.registerUser(signUpRequest);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error :" + e.getMessage());
        }
    }
}
