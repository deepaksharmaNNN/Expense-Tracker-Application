package com.deepak.sharma.authservice.common;

import com.deepak.sharma.authservice.dtos.response.JwtResponseDTO;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class CommonMethods {

    public static ResponseEntity<?> buildJwtResponse(String jwtToken, String refreshToken) {
        return new ResponseEntity<>(
                JwtResponseDTO.builder()
                        .accessToken(jwtToken)
                        .token(refreshToken)
                        .build(),
                HttpStatus.OK
        );
    }
}
