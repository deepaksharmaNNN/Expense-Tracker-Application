package com.deepak.sharma.authservice.controller;

import com.deepak.sharma.authservice.dto.request.SignupRequest;
import com.deepak.sharma.authservice.dto.response.JwtResponse;
import com.deepak.sharma.authservice.entity.RefreshToken;
import com.deepak.sharma.authservice.service.AuthService;
import com.deepak.sharma.authservice.service.JwtService;
import com.deepak.sharma.authservice.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/") // http://localhost:8081/v1/auth/
    public String home() {
        return "Welcome to Auth Service!";
    }

    @PostMapping("/auth/v1/register") // http://localhost:8081/v1/auth/auth/v1/register
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            Boolean signUpSucceed = authService.registerUser(signUpRequest);
            if(Boolean.FALSE.equals(signUpSucceed)){
                return new ResponseEntity<>("Already registered!", HttpStatus.CONFLICT);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(signUpRequest.getUsername());
            String jwtToken = jwtService.generateToken(signUpRequest.getUsername());
            return new ResponseEntity<>(JwtResponse.builder().accessToken(jwtToken)
                    .refreshToken(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error :" + e.getMessage());
        }
    }
}
