package com.deepak.sharma.authservice.controller;

import com.deepak.sharma.authservice.dto.request.LoginRequest;
import com.deepak.sharma.authservice.dto.request.RefreshTokenRequest;
import com.deepak.sharma.authservice.dto.response.JwtResponse;
import com.deepak.sharma.authservice.entity.RefreshToken;
import com.deepak.sharma.authservice.service.JwtService;
import com.deepak.sharma.authservice.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/v1/login") // http://localhost:8081/v1/auth/auth/v1/login
    public ResponseEntity<?> AuthenticateAndLogin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());
            return new ResponseEntity<>(JwtResponse.builder()
                    .accessToken(jwtService.generateToken(loginRequest.getUsername()))
                    .refreshToken(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("auth/v1/refresh") // http://localhost:8081/v1/auth/auth/v1/refresh
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getUsername());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }
}
