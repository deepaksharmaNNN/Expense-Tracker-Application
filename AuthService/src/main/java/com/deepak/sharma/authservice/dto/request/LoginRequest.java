package com.deepak.sharma.authservice.dto.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginRequest {
    String username;
    String password;
}
