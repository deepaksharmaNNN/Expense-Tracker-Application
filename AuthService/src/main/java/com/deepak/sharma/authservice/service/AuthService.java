package com.deepak.sharma.authservice.service;

import com.deepak.sharma.authservice.dto.request.SignupRequest;
import com.deepak.sharma.authservice.entity.Role;
import com.deepak.sharma.authservice.entity.User;
import com.deepak.sharma.authservice.events.producer.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserProducer userProducer;


    public Boolean registerUser(SignupRequest signupRequest) {
        if (userService.findByUsername(signupRequest.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        Set<Role> roles = new HashSet<>();
        for (String roleName : signupRequest.getRoles()) {
            Role role = roleService.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }

        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .roles(roles)
                .build();

        userService.save(user);
        // send event to kafka
        return Boolean.TRUE;
    }
}
