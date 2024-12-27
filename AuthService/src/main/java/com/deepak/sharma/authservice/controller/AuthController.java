package com.deepak.sharma.authservice.controller;

import com.deepak.sharma.authservice.dtos.UserInfoDto;
import com.deepak.sharma.authservice.dtos.response.JwtResponseDTO;
import com.deepak.sharma.authservice.entities.RefreshToken;
import com.deepak.sharma.authservice.service.JwtService;
import com.deepak.sharma.authservice.service.RefreshTokenService;
import com.deepak.sharma.authservice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/auth/v1/signup") // http://localhost:8080/auth/v1/signup
    public ResponseEntity<?> SignUp(@RequestBody UserInfoDto userInfoDto) {
        try{
            Boolean isSignedUp = userDetailsService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignedUp)){
                return new ResponseEntity<>("User already exist", HttpStatus.CONFLICT);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUserName());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUserName());
            return buildJwtResponse(jwtToken, refreshToken.getToken());
        }catch (Exception e){
            return new ResponseEntity<>("Error while signing up", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private ResponseEntity<?> buildJwtResponse(String jwtToken, String refreshToken) {
        return new ResponseEntity<>(
                JwtResponseDTO.builder()
                        .accessToken(jwtToken)
                        .token(refreshToken)
                        .build(),
                HttpStatus.OK
        );
    }

}
