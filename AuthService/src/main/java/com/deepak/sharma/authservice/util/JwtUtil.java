package com.deepak.sharma.authservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String secretKey = System.getenv("JWT_SECRET");
    private final long jwtExpiration = Long.parseLong(System.getenv("JWT_ExpirationMs"));

    // Validate token
    
}
