package com.deepak.sharma.authservice.controller;

import com.deepak.sharma.authservice.dtos.request.AuthRequestDTO;
import com.deepak.sharma.authservice.dtos.request.RefreshTokenRequestDTO;
import com.deepak.sharma.authservice.dtos.response.JwtResponseDTO;
import com.deepak.sharma.authservice.entities.RefreshToken;
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

import static com.deepak.sharma.authservice.common.CommonMethods.buildJwtResponse;

@RestController
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/auth/v1/login") // http://localhost:8080/auth/v1/login
    public ResponseEntity<?> AuthenticateAndGenerateToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUserName(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUserName());
            return buildJwtResponse(jwtService.generateToken(authRequestDTO.getUserName()), refreshToken.getToken());
        }else {
            return new ResponseEntity<>("Exception in user service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/v1/refreshToken") // http://localhost:8080/auth/v1/refreshToken
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUserName());
                    return new JwtResponseDTO(accessToken, refreshTokenRequestDTO.getRefreshToken());
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
